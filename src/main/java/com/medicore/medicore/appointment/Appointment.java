package com.medicore.medicore.appointment;

import com.medicore.medicore.doctor.DoctorProfile;
import com.medicore.medicore.patient.PatientProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientProfile patient;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdTime;

    private LocalDateTime appointmentDateTime;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    private Long amountInPaise;
    private String currency = "INR";

    @PrePersist
    public void setCreatedTime(){
        this.createdTime = LocalDateTime.now();
        this.appointmentStatus = AppointmentStatus.REQUESTED;
    }

}
