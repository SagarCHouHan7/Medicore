package com.medicore.medicore.doctor.dto;

import com.medicore.medicore.account.entity.Role;
import com.medicore.medicore.doctor.ApprovalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DoctorProfileResponse(
        Long DoctorId,
        Long userId,
        Long hospitalId,
        String fullName,
        String email,
        Role role,
        String about,
        String gender,
        String phoneNumber,
        String specialization,
        String qualification,
        Integer experienceYears,
        String address,
        Integer consultationFees,
        LocalDate dateOfBirth,
        LocalDateTime joinedOn,
        boolean isVerificationSubmitted,
        boolean isVerified,
        ApprovalStatus approvalStatus
) {
}
