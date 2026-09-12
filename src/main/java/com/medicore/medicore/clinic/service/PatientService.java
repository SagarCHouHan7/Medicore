package com.medicore.medicore.clinic.service;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.PatientProfile;
import com.medicore.medicore.clinic.repository.PatientProfileRepository;
import com.medicore.medicore.comman.exception.custome.ProfileNotFoundException;
import com.medicore.medicore.comman.mapper.PatientMapper;
import com.medicore.medicore.clinic.dto.PatientProfileRequest;
import com.medicore.medicore.clinic.dto.PatientProfileResponse;
import com.medicore.medicore.comman.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientProfileRepository patientProfileRepository;
    private final UserUtils userUtils;

    public PatientProfileResponse createProfile(PatientProfileRequest request) throws BadRequestException {
        User user = userUtils.getCurrentUser();

        if (patientProfileRepository.findByUser(user).isPresent()) {
            throw new BadRequestException(
                    "Profile already exists"
            );
        }
        PatientProfile profile = setPatientProfile(request, user);
        PatientProfile save = patientProfileRepository.save(profile);
        return PatientMapper.mapPatientProfileResponse(save);
    }

    public PatientProfileResponse getProfile(){
        User user = userUtils.getCurrentUser();
        PatientProfile profile = patientProfileRepository.findByUser(user)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not created yet"));
        return PatientMapper.mapPatientProfileResponse(profile);
    }

    private PatientProfile setPatientProfile(PatientProfileRequest request, User user){
        PatientProfile patientProfile = new PatientProfile();
        patientProfile.setAddress(request.address());
        patientProfile.setBloodGroup(request.bloodGroup());
        patientProfile.setDateOfBirth(request.dateOfBirth());
        patientProfile.setGender(request.gender());
        patientProfile.setPhoneNumber(request.phoneNumber());
        patientProfile.setUser(user);
        return patientProfile;
    }


}
