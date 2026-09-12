package com.medicore.medicore.clinic.dto;

import com.medicore.medicore.clinic.model.PrescriptionMedicine;

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
