package com.medicore.medicore.billing.service;

import com.medicore.medicore.account.entity.Role;
import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.billing.model.dto.*;
import com.medicore.medicore.billing.model.entity.Payment;
import com.medicore.medicore.billing.model.entity.Settlement;
import com.medicore.medicore.billing.model.status.PaymentStatus;
import com.medicore.medicore.billing.model.status.PayoutStatus;
import com.medicore.medicore.billing.model.status.SettlementStatus;
import com.medicore.medicore.billing.repository.PaymentRepository;
import com.medicore.medicore.billing.repository.SettlementRepository;
import com.medicore.medicore.comman.exception.custome.CustomeExeption;
import com.medicore.medicore.comman.exception.custome.DoctorNotFoundException;
import com.medicore.medicore.comman.exception.custome.HospitalNotFoundException;
import com.medicore.medicore.comman.utils.UserUtils;
import com.medicore.medicore.clinic.model.DoctorProfile;
import com.medicore.medicore.clinic.repository.DoctorProfileRepository;
import com.medicore.medicore.clinic.model.HospitalProfile;
import com.medicore.medicore.clinic.repository.HospitalRepo;
import com.medicore.medicore.clinic.repository.PatientProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserUtils userUtils;
    private final PaymentRepository paymentRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final HospitalRepo hospitalRepo;

    public List<Settlement> getAllSettlements(){
        User user = userUtils.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) throw new CustomeExeption("you don't have right to view this resource");
        return settlementRepository.findAllByOrderBySettledAtDesc();
    }

    public List<Settlement> getUnsettledSettlements(SettlementStatus status){
        User user = userUtils.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) throw new CustomeExeption("you don't have right to view this resource");
        return settlementRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    @Transactional
    public Settlement settleSettlement(Long id){
        User user = userUtils.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) throw new CustomeExeption("you don't have right to view this resource");

        Settlement settlement = settlementRepository.findById(id)
                .orElseThrow(()-> new CustomeExeption("Settlement not found"));

        if(settlement.getStatus() == SettlementStatus.SETTLED) throw  new CustomeExeption("already settled");

        settlement.setSettledAt(LocalDateTime.now());
        settlement.setStatus(SettlementStatus.SETTLED);

        return settlementRepository.save(settlement);
    }

    public DoctorDashboardResponse getDoctorDashboard() {

        User user = userUtils.getCurrentUser();

        DoctorProfile doctor = doctorProfileRepository.findByUser(user)
                .orElseThrow(DoctorNotFoundException::new);

        List<Payment> payments = paymentRepository
                .findByStatusAndBill_DoctorProfile(PaymentStatus.SUCCESS, doctor);

        Map<YearMonth, List<Payment>> grouped =
                payments.stream()
                        .collect(Collectors.groupingBy(
                                p -> YearMonth.from(p.getPaidAt())
                        ));

        List<DoctorMonthlyEarningDto> response = grouped.entrySet()
                .stream()
                .sorted(Map.Entry.<YearMonth, List<Payment>>comparingByKey().reversed())
                .map(entry -> {

                    YearMonth month = entry.getKey();
                    List<Payment> monthPayments = entry.getValue();

                    long appointments = monthPayments.size();

                    long revenue = monthPayments.stream()
                            .mapToLong(p -> p.getBill().getAmountInPaise())
                            .sum();

                    long receivable = monthPayments.stream()
                            .mapToLong(Payment::getDoctorShareInPaise)
                            .sum();

                    PayoutStatus status = monthPayments.stream()
                            .allMatch(p -> p.getHospitalPayout() != null
                                    && p.getHospitalPayout().getStatus() == PayoutStatus.PAID)
                            ? PayoutStatus.PAID
                            : PayoutStatus.PENDING;

                    return new DoctorMonthlyEarningDto(
                            month,
                            appointments,
                            revenue,
                            receivable,
                            status
                    );
                })
                .toList();

        return new DoctorDashboardResponse(response);
    }


    public HospitalDashboardResponse getHospitalDashboard() {

        User user = userUtils.getCurrentUser();

        HospitalProfile hospital = hospitalRepo.findByHospitalAdministrator(user)
                .orElseThrow(HospitalNotFoundException::new);

        List<Payment> payments = paymentRepository
                .findByStatusAndBill_HospitalProfile(
                        PaymentStatus.SUCCESS,
                        hospital
                );

        Map<String, Map<YearMonth, List<Payment>>> grouped =
                payments.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getBill().getDoctorProfile().getUser().getFullName(),
                                Collectors.groupingBy(
                                        p -> YearMonth.from(p.getPaidAt())
                                )
                        ));

        List<HospitalDoctorMonthlyDto> earnings = new ArrayList<>();

        grouped.forEach((doctorName, monthMap) -> {

            monthMap.forEach((month, doctorPayments) -> {

                long totalPatients = doctorPayments.stream()
                        .map(p -> p.getBill().getPaidBy().getId())
                        .distinct()
                        .count();

                long appointments = doctorPayments.size();

                long revenue = doctorPayments.stream()
                        .mapToLong(p -> p.getBill().getAmountInPaise())
                        .sum();

                long hospitalShare = doctorPayments.stream()
                        .mapToLong(Payment::getHospitalShareInPaise)
                        .sum();

                long doctorShare = doctorPayments.stream()
                        .mapToLong(Payment::getDoctorShareInPaise)
                        .sum();

                earnings.add(new HospitalDoctorMonthlyDto(
                        doctorName,
                        month,
                        totalPatients,
                        appointments,
                        revenue,
                        hospitalShare,
                        doctorShare
                ));
            });

        });

        earnings.sort(Comparator
                .comparing(HospitalDoctorMonthlyDto::month)
                .reversed());

        return new HospitalDashboardResponse(earnings);
    }

    public AdminDashboardResponse getAdminDashboard() {

        List<Payment> payments = paymentRepository
                .findByStatus(PaymentStatus.SUCCESS);

        Map<String, Map<YearMonth, List<Payment>>> grouped =
                payments.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getBill().getHospitalProfile().getHospitalName(),
                                Collectors.groupingBy(
                                        p -> YearMonth.from(p.getPaidAt())
                                )
                        ));

        List<AdminHospitalMonthlyDto> earnings = new ArrayList<>();

        grouped.forEach((hospitalName, monthMap) -> {

            monthMap.forEach((month, hospitalPayments) -> {

                long totalPatients = hospitalPayments.stream()
                        .map(p -> p.getBill().getPaidBy().getId())
                        .distinct()
                        .count();

                long totalAppointments = hospitalPayments.size();

                long totalRevenue = hospitalPayments.stream()
                        .mapToLong(p -> p.getBill().getAmountInPaise())
                        .sum();

                long platformRevenue = hospitalPayments.stream()
                        .mapToLong(Payment::getPlatformShareInPaise)
                        .sum();

                long amountPayableToHospital = hospitalPayments.stream()
                        .mapToLong(p ->
                                p.getHospitalShareInPaise() +
                                        p.getDoctorShareInPaise())
                        .sum();

                earnings.add(new AdminHospitalMonthlyDto(
                        hospitalName,
                        month,
                        totalPatients,
                        totalAppointments,
                        totalRevenue,
                        platformRevenue,
                        amountPayableToHospital,
                        PayoutStatus.PENDING
                ));
            });

        });

        earnings.sort(Comparator
                .comparing(AdminHospitalMonthlyDto::month)
                .reversed());

        return new AdminDashboardResponse(earnings);
    }


}
