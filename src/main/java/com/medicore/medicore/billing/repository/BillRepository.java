package com.medicore.medicore.billing.repository;

import com.medicore.medicore.billing.model.entity.Bill;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.doctor.DoctorProfile;
import com.medicore.medicore.patient.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByPaidByOrderByGeneratedAtDesc(PatientProfile patientProfile);

    Optional<Bill> findByAppointmentId(Long id);

    List<Bill> findByDoctorProfileOrderByGeneratedAtDesc(DoctorProfile doctorProfile);


    List<Bill> findByPaidByAndBillingStatusOrderByGeneratedAtDesc(
            PatientProfile patient,
            BillingStatus status
    );}
