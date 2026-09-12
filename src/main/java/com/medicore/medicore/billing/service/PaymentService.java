package com.medicore.medicore.billing.service;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.Appointment;
import com.medicore.medicore.clinic.repository.AppointmentRepo;
import com.medicore.medicore.clinic.model.AppointmentStatus;
import com.medicore.medicore.billing.model.dto.BillingResponseDto;
import com.medicore.medicore.billing.model.entity.Bill;
import com.medicore.medicore.billing.model.entity.Payment;
import com.medicore.medicore.billing.model.entity.Settlement;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.billing.model.status.PaymentStatus;
import com.medicore.medicore.billing.model.status.SettlementStatus;
import com.medicore.medicore.billing.repository.BillRepository;
import com.medicore.medicore.billing.repository.PaymentRepository;
import com.medicore.medicore.billing.repository.SettlementRepository;
import com.medicore.medicore.comman.exception.custome.CustomeExeption;
import com.medicore.medicore.comman.mapper.BillingMapper;
import com.medicore.medicore.clinic.model.PatientProfile;
import com.medicore.medicore.clinic.repository.PatientProfileRepository;
import com.medicore.medicore.comman.utils.UserUtils;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AppointmentRepo appointmentRepo;
    private final PatientProfileRepository patientProfileRepository;
    private final UserUtils userUtils;
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final SettlementRepository settlementRepository;
    private static final int PLATFORM_PERCENTAGE = 10;
    private static final int HOSPITAL_PERCENTAGE = 20;

    @Value("${razorpay.key.id}")
    private String razorpayId;

    @Value("${razorpay.key.secret}")
    private String razorpayKey;

    public Map<String, Object> createOrder(Long id) throws RazorpayException {
        log.info("inside service");
        Bill bill = billRepository.findByAppointmentId(id)
                .orElseThrow(()->new CustomeExeption("bill not found"));

        log.info("bill fetched");

        validateAuthenticity(bill);

        log.info("valid success");

        Payment payment = paymentRepository.findByBill(bill).orElse(null);

        if (payment != null) {

            if (payment.getStatus() == PaymentStatus.SUCCESS) {
                throw new CustomeExeption("Bill already paid");
            }

            if (payment.getOrderId() != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("razorpayOrderId", payment.getOrderId());
                response.put("amountInPaise", bill.getAmountInPaise());
                response.put("currency", "INR");
                response.put("keyId", razorpayId);
                log.info("response of previos order  {}", response);
                return response;
            }
        }

        log.info("payment fetched");
        if (payment == null) payment = new Payment();
        payment.setBill(bill);
        payment.setStatus(PaymentStatus.CREATED);
        payment.setGateway("RAZORPAY");

        JSONObject options = new JSONObject();
        options.put("amount", bill.getAmountInPaise());
        options.put("currency", "INR");
        options.put("receipt", "bill_" + bill.getId());

        RazorpayClient client = new RazorpayClient(razorpayId, razorpayKey);
        Order order = client.orders.create(options);

        payment.setOrderId(order.get("id"));
        paymentRepository.save(payment);

        Map<String, Object> response = new HashMap<>();
        response.put("razorpayOrderId", payment.getOrderId());
        response.put("amountInPaise", bill.getAmountInPaise());
        response.put("currency", "INR");
        response.put("keyId", razorpayId);
        return response;
    }

    private void validateAuthenticity(Bill bill) {
        User user = userUtils.getCurrentUser();
        PatientProfile patientProfile = patientProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (!bill.getPaidBy().getId().equals(patientProfile.getId()))
            throw new CustomeExeption("you don't own this bill");

        if (bill.getBillingStatus() != BillingStatus.PENDING) throw new CustomeExeption("bill is already paid");

        if (bill.getAppointment().getAppointmentStatus() != AppointmentStatus.PAYMENT_PENDING)
            throw new CustomeExeption("Action not allowed");
    }

    @Transactional
    public BillingResponseDto verify(Map<String, String> data) throws Exception {
        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomeExeption("Invalid order"));

        Bill bill = payment.getBill();
        Appointment appointment = bill.getAppointment();

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return BillingMapper.mapToBillingResponseDto(bill);
        }

        if (bill.getBillingStatus() != BillingStatus.PENDING)
            throw new CustomeExeption("Action Not allowed");

        String generatedSignature = HmacSHA256(orderId + "|" + paymentId, razorpayKey);

        if (!generatedSignature.equals(signature)) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new CustomeExeption("Invalid payment");
        }

        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointmentRepo.save(appointment);

        bill.setBillingStatus(BillingStatus.PAID);
        bill.setPaidAt(LocalDateTime.now());
        billRepository.save(bill);

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentId(paymentId);
        payment.setPaidAt(LocalDateTime.now());

        calculateShares(payment);
        paymentRepository.save(payment);

        return BillingMapper.mapToBillingResponseDto(bill);
    }

    private String HmacSHA256(String data, String razorpayKey) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(razorpayKey.getBytes(), "HmacSHA256");
        mac.init(secretKey);

        byte[] hash = mac.doFinal(data.getBytes());

        StringBuilder hex = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) {
                hex.append('0');
            }
            hex.append(s);
        }
        return hex.toString();
    }

    private void calculateShares(Payment payment) {
        Bill bill = payment.getBill();
        long total = bill.getAmountInPaise();

        long platform = total * PLATFORM_PERCENTAGE / 100;
        long hospital = (total - platform) * HOSPITAL_PERCENTAGE / 100;
        long doctor = total - platform - hospital;

        payment.setPlatformShareInPaise(platform);
        payment.setHospitalShareInPaise(hospital);
        payment.setDoctorShareInPaise(doctor);
    }


}
