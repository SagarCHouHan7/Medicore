package com.medicore.medicore.comman.mapper;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.DoctorProfile;
import com.medicore.medicore.clinic.dto.DoctorProfileResponse;

public class DoctorMapper {

    public static DoctorProfileResponse mapDoctorProfileResponse(DoctorProfile profile) {
        User user = profile.getUser();
        return new DoctorProfileResponse(
                profile.getId(),
                user.getId(),
                profile.getHospitalProfile().getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                profile.getAbout(),
                profile.getGender(),
                profile.getPhoneNumber(),
                profile.getSpecialization(),
                profile.getQualification(),
                profile.getExperienceYears(),
                profile.getAddress(),
                profile.getConsultationFees(),
                profile.getDateOfBirth(),
                user.getCreatedAt(),
                profile.isVerificationSubmitted(),
                profile.isVerified(),
                profile.getApprovalStatus()
        );
    }

}
