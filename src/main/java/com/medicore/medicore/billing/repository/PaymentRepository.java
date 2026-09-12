package com.medicore.medicore.billing.repository;

import com.medicore.medicore.billing.model.entity.Bill;
import com.medicore.medicore.billing.model.entity.Payment;
import com.medicore.medicore.billing.model.status.PaymentStatus;
import com.medicore.medicore.clinic.model.DoctorProfile;
import com.medicore.medicore.clinic.model.HospitalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByBill(Bill bill);

    List<Payment> findByStatusAndBill_DoctorProfile(
            PaymentStatus status,
            DoctorProfile doctorProfile
    );

    List<Payment> findByStatusAndBill_HospitalProfile(
            PaymentStatus status,
            HospitalProfile hospitalProfile
    );

    List<Payment> findByStatus(PaymentStatus paymentStatus);
}
