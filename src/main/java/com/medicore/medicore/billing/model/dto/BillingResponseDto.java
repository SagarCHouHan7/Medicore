package com.medicore.medicore.billing.model.dto;

import com.medicore.medicore.clinic.dto.AppointmentResponse;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.clinic.dto.HospitalResponseDto;
import com.medicore.medicore.clinic.dto.PatientProfileResponse;
import java.time.LocalDateTime;

public record BillingResponseDto(
        Long id,
        BillingStatus billingStatus,
        Integer amountInPaise,
        PatientProfileResponse paidBy,
        HospitalResponseDto hospitalProfile,
        AppointmentResponse appointment,
        LocalDateTime generatedAt,
        LocalDateTime paidAt
        ) {
}
