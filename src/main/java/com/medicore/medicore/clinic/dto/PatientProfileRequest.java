package com.medicore.medicore.clinic.dto;

import java.time.LocalDate;

public record PatientProfileRequest(

        String gender,
        String phoneNumber,
        String address,
        String bloodGroup,
        LocalDate dateOfBirth
) {
}
