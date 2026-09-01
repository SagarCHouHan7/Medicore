package com.medicore.medicore.billing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicore.medicore.billing.model.status.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    private String orderId;

    private String paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String gateway = "RAZORPAY";

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "hospital_payout_id")
    @JsonIgnore
    private HospitalPayout hospitalPayout;

    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    private Long platformShareInPaise;

    private Long hospitalShareInPaise;

    private Long doctorShareInPaise;

}
