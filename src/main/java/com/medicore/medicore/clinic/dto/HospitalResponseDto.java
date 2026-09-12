package com.medicore.medicore.clinic.dto;

import com.medicore.medicore.account.entity.User;

import java.time.LocalDateTime;

public record HospitalResponseDto(
        Long hospitalId,
        String HospitalName,
        LocalDateTime registeredOn,
        Long licenceNo,
        String hospitalType,
        String about,
        String address,
        String city,
        String state,
        String zipCode,
        String phoneNo,
        String customerCareEmail,
        String timing,
        boolean isVerified,
        boolean isActive,
        boolean hasEmergencyServices,
        boolean isVerificationSubmitted
) {
}

