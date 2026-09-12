package com.medicore.medicore.clinic.dto;

import java.time.LocalDateTime;

public record AppointmentRequest(
        Long doctorId,
        Long patientId,
        LocalDateTime appointmentDateTime,
        String reason
) {
}
