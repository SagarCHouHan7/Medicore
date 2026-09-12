package com.medicore.medicore.clinic.dto;

import jakarta.validation.constraints.NotBlank;

public record HospitalProfileCreationDto(
        @NotBlank
        String hospitalName,
        @NotBlank
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
        boolean hasEmergencyServices
) {
}
