package com.medicore.medicore.hospital;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.doctor.DoctorProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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

