package com.medicore.medicore.appointment.dto;

import java.time.LocalDateTime;

public record AppointmentRequest(
        Long doctorId,
        Long patientId,
        LocalDateTime appointmentDateTime,
        String reason
) {
}
