package com.medicore.medicore.patient.dto;

import com.medicore.medicore.account.entity.Role;

import java.time.LocalDate;

public record PatientProfileResponse(
        Long PatientId,
        Long userId,
        String fullName,
        String email,
        Role role,
        String gender,
        String bloodGroup,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address
) {
}
