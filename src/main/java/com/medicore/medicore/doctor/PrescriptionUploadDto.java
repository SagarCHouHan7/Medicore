package com.medicore.medicore.doctor;

import java.time.LocalDate;
import java.util.List;

public record PrescriptionUploadDto(
        Long appointmentId,
        LocalDate followUpDate,
        List<PrescriptionMedicine> prescriptionMedicines
) {
}
