package com.medicore.medicore.billing.model.entity;

import com.medicore.medicore.appointment.Appointment;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.doctor.DoctorProfile;
import com.medicore.medicore.hospital.HospitalProfile;
import com.medicore.medicore.patient.PatientProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    private long amountInPaise;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientProfile paidBy;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctorProfile;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalProfile hospitalProfile;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    private LocalDateTime generatedAt;

    private LocalDateTime paidAt;

    @PrePersist
    public void setGeneratedAt() {
        this.generatedAt = LocalDateTime.now();
    }

}
