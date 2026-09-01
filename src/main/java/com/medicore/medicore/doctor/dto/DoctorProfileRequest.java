package com.medicore.medicore.doctor.dto;

import java.time.LocalDate;

public record DoctorProfileRequest(
        String about,
        String gender,
        String phoneNumber,
        String specialization,
        String qualification,
        Integer experienceYears,
        String address,
        Integer consultationFees,
        LocalDate dateOfBirth,
        Long hospitalId
) {
}
