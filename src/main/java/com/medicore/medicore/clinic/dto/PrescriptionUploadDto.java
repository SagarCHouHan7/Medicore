package com.medicore.medicore.clinic.dto;

import com.medicore.medicore.clinic.model.PrescriptionMedicine;

import java.time.LocalDate;
import java.util.List;

public record PrescriptionUploadDto(
        Long appointmentId,
        LocalDate followUpDate,
        List<PrescriptionMedicine> prescriptionMedicines
) {
}
