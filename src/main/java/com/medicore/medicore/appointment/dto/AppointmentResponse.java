package com.medicore.medicore.appointment.dto;

import com.medicore.medicore.appointment.AppointmentStatus;
import com.medicore.medicore.doctor.dto.DoctorProfileResponse;
import com.medicore.medicore.patient.dto.PatientProfileResponse;
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
