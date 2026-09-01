package com.medicore.medicore.doctor;

import com.medicore.medicore.appointment.Appointment;
import com.medicore.medicore.patient.PatientProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctorProfile;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientProfile patientProfile;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private LocalDate prescriptionDate;

    private LocalDate followUpDate;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PrescriptionMedicine> prescriptionMedicines;

    @PrePersist
    public void setPrescriptionDate() {
        this.prescriptionDate = LocalDate.now();
    }

}
