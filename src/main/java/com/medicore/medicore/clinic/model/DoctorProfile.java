package com.medicore.medicore.clinic.model;

import com.medicore.medicore.account.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "doctor_profiles")
@Getter
@Setter
public class DoctorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 4000)
    private String about;

    private String gender;

    private String specialization;

    private String qualification;

    private Integer experienceYears;

    private String address;

    private boolean isVerified;

    private boolean isVerificationSubmitted;

    private int consultationFees;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalProfile hospitalProfile;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Appointment> appointments;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.WAITING_FOR_APPROVAL;
}
