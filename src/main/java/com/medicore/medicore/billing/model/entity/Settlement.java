package com.medicore.medicore.billing.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicore.medicore.billing.model.status.SettlementStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    @JsonIgnore
    private Payment payment;


    @Enumerated(EnumType.STRING)
    private SettlementStatus status;

    private LocalDateTime settledAt = null;

    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
