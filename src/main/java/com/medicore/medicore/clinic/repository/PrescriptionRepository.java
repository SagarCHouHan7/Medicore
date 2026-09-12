package com.medicore.medicore.clinic.repository;

import com.medicore.medicore.clinic.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByAppointment_Id(Long appointmentId);


    List<Prescription> findByPatientProfile_Id(Long patientId);

    List<Prescription> findByDoctorProfile_Id(Long doctorId);
}
