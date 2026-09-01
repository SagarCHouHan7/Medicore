package com.medicore.medicore.patient;

import com.medicore.medicore.account.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "patient_profiles")
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    private String address;

    private String bloodGroup;

}
