package com.medicore.medicore.clinic.dto;

import com.medicore.medicore.clinic.model.AppointmentStatus;
import com.medicore.medicore.billing.model.status.PaymentStatus;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        PatientProfileResponse patientProfileResponse,
        DoctorProfileResponse doctorProfileResponse,
        LocalDateTime appointmentDateTime,
        String reason,
        AppointmentStatus appointmentStatus,
        LocalDateTime createdTime,
        int amountInPaise,
        String currency,
        String hospitalName,
        String address
) {
}
