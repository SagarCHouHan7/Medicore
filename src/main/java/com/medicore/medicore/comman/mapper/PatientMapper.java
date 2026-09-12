package com.medicore.medicore.comman.mapper;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.PatientProfile;
import com.medicore.medicore.clinic.dto.PatientProfileResponse;

public class PatientMapper {

    public static PatientProfileResponse mapPatientProfileResponse(PatientProfile profile){
        User user = profile.getUser();
        return new PatientProfileResponse(
                profile.getId(),
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                profile.getGender(),
                profile.getBloodGroup(),
                profile.getDateOfBirth(),
                profile.getPhoneNumber(),
                profile.getAddress()
        );
    }
}
