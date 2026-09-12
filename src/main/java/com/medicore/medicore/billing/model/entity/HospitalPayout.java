package com.medicore.medicore.billing.model.entity;

import com.medicore.medicore.billing.model.status.PayoutStatus;
import com.medicore.medicore.clinic.model.HospitalProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class HospitalPayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalProfile hospital;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long totalCollectedInPaise;

    private Long platformShareInPaise;

    private Long hospitalShareInPaise;

    private Long doctorShareInPaise;

    @Enumerated(EnumType.STRING)
    private PayoutStatus status;

    private LocalDateTime paidAt;

    @OneToMany(mappedBy = "hospitalPayout")
    private List<Payment> payments;
}