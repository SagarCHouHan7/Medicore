package com.medicore.medicore.comman.mapper;

import com.medicore.medicore.doctor.Prescription;
import com.medicore.medicore.doctor.PrescriptionResponseDto;

public class PrescriptionMapper {

    public static PrescriptionResponseDto mapPrescriptionToResponseDto(Prescription prescription) {
        return new PrescriptionResponseDto(
                prescription.getId(),
                AppointmentMapper.mapAppointmentResponse(prescription.getAppointment()),
                prescription.getPrescriptionDate(),
                prescription.getFollowUpDate(),
                prescription.getPrescriptionMedicines()
        );
    }
}
