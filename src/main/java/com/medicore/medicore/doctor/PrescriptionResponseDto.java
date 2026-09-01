package com.medicore.medicore.doctor;

import com.medicore.medicore.appointment.dto.AppointmentResponse;
import java.time.LocalDate;
import java.util.List;

public record PrescriptionResponseDto(
        Long id,
        AppointmentResponse appointmentResponse,
        LocalDate prescriptionDate,
        LocalDate followUpDate,
        List<PrescriptionMedicine> prescriptionMedicines
) {
}
