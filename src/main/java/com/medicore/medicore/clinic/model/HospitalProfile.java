package com.medicore.medicore.clinic.model;

import com.medicore.medicore.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class HospitalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hospitalName;
    private LocalDateTime registeredOn;
    private Long licenceNo;
    private String hospitalType;

    @Column(length = 4000)
    private String about;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNo;
    private String customerCareEmail;
    private String timing;
    private boolean isActive = false;
    private boolean isVerified = false;
    private boolean hasEmergencyServices;
    private boolean isVerificationSubmitted;

    @OneToMany(mappedBy = "hospitalProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DoctorProfile> doctorProfiles;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User hospitalAdministrator;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User registeredBy;

    @PrePersist
    private void setRegisteredOn() {
        this.registeredOn = LocalDateTime.now();
    }
}
