package com.medicore.medicore.clinic.service;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.DoctorProfile;
import com.medicore.medicore.clinic.repository.DoctorProfileRepository;
import com.medicore.medicore.comman.exception.custome.HospitalNotFoundException;
import com.medicore.medicore.clinic.dto.DoctorProfileRequest;
import com.medicore.medicore.clinic.dto.DoctorProfileResponse;
import com.medicore.medicore.comman.mapper.DoctorMapper;
import com.medicore.medicore.clinic.model.HospitalProfile;
import com.medicore.medicore.clinic.repository.HospitalRepo;
import com.medicore.medicore.clinic.repository.PatientProfileRepository;
import com.medicore.medicore.comman.exception.custome.ProfileNotFoundException;
import com.medicore.medicore.comman.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final UserUtils userUtils;
    private final PatientProfileRepository patientProfileRepository;
    private final HospitalRepo hospitalRepo;

    public DoctorProfileResponse createProfile(DoctorProfileRequest request) throws BadRequestException {

        User user = userUtils.getCurrentUser();

        if (doctorProfileRepository.findByUser(user).isPresent()) {
            throw new BadRequestException(
                    "Profile already exists"
            );
        }

        DoctorProfile profile = setDoctorProfile(request, user);
        DoctorProfile save = doctorProfileRepository.save(profile);
        return DoctorMapper.mapDoctorProfileResponse(save);
    }

    public DoctorProfileResponse getProfile(){
        User user = userUtils.getCurrentUser();
        DoctorProfile profile = doctorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ProfileNotFoundException("profile not created yet"));
        return DoctorMapper.mapDoctorProfileResponse(profile);
    }

    public List<DoctorProfileResponse> getAllDoctors(){
        return doctorProfileRepository.findAll().stream()
                .map(DoctorMapper::mapDoctorProfileResponse)
                .collect(Collectors.toList());
    }

    public DoctorProfileResponse getDoctorById(Long id){
        DoctorProfile profile = doctorProfileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException("profile not found"));
        return DoctorMapper.mapDoctorProfileResponse(profile);
    }

    private DoctorProfile setDoctorProfile(DoctorProfileRequest request, User user) {
        HospitalProfile hospitalProfile = hospitalRepo.findById(request.hospitalId())
                .orElseThrow(()-> new HospitalNotFoundException("Hosipital not available"));
        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setHospitalProfile(hospitalProfile);
        doctorProfile.setAbout(request.about());
        doctorProfile.setGender(request.gender());
        doctorProfile.setPhoneNumber(request.phoneNumber());
        doctorProfile.setSpecialization(request.specialization());
        doctorProfile.setQualification(request.qualification());
        doctorProfile.setExperienceYears(request.experienceYears());
        doctorProfile.setAddress(request.address());
        doctorProfile.setConsultationFees(request.consultationFees());
        doctorProfile.setDateOfBirth(request.dateOfBirth());
        doctorProfile.setVerificationSubmitted(true);
        doctorProfile.setUser(user);
        return doctorProfile;
    }
}
