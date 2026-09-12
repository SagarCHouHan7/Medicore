package com.medicore.medicore.clinic.repository;

import com.medicore.medicore.clinic.model.DoctorProfile;
import com.medicore.medicore.clinic.model.Appointment;
import com.medicore.medicore.clinic.model.AppointmentStatus;
import com.medicore.medicore.clinic.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(PatientProfile patientProfile);

    List<Appointment> findByDoctor(DoctorProfile doctorProfile);

    Optional<Appointment> findByIdAndPatient(Long id, PatientProfile patientProfile);

    Optional<Appointment> findByIdAndDoctor(Long id, DoctorProfile doctorProfile);

   @Query("SELECT a FROM Appointment a JOIN a.doctor d JOIN d.hospitalProfile h WHERE h.id = :hospitalId AND a.appointmentDateTime BETWEEN :start AND :end AND a.appointmentStatus IN :statuses")
   List<Appointment> findByHospitalAndDateRange(Long hospitalId, java.time.LocalDateTime start, java.time.LocalDateTime end, List<AppointmentStatus> statuses);

}
