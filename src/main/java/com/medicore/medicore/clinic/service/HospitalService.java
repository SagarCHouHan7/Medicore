package com.medicore.medicore.clinic.service;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.dto.HospitalDashboardStats;
import com.medicore.medicore.clinic.dto.HospitalResponseDto;
import com.medicore.medicore.clinic.repository.HospitalRepo;
import com.medicore.medicore.clinic.model.*;
import com.medicore.medicore.clinic.repository.AppointmentRepo;
import com.medicore.medicore.clinic.dto.HospitalProfileCreationDto;
import com.medicore.medicore.comman.exception.custome.CustomeExeption;
import com.medicore.medicore.comman.exception.custome.HospitalNotFoundException;
import com.medicore.medicore.comman.exception.custome.UnauthorizeAccessException;
import com.medicore.medicore.comman.mapper.DoctorMapper;
import com.medicore.medicore.comman.mapper.HospitalMapper;
import com.medicore.medicore.comman.utils.UserUtils;
import com.medicore.medicore.clinic.repository.DoctorProfileRepository;
import com.medicore.medicore.clinic.dto.DoctorProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepo hospitalRepo;
    private final UserUtils userUtils;
    private final AppointmentRepo appointmentRepo;
    private final DoctorProfileRepository doctorProfileRepository;

    public HospitalResponseDto createHospitalProfile(HospitalProfileCreationDto dto){

        User hospitalAdministrator = userUtils.getCurrentUser();
        HospitalProfile hospitalProfile = hospitalRepo.findByHospitalAdministrator(hospitalAdministrator)
                .orElseThrow(HospitalNotFoundException::new);

        HospitalProfile hospitalProfile1 = HospitalMapper.setHospitalProfile(hospitalProfile, dto);
        hospitalProfile1.setVerificationSubmitted(true);
        HospitalProfile savedHospitalProfile = hospitalRepo.save(hospitalProfile1);
        return HospitalMapper.mapToHospitalResponseDto(savedHospitalProfile);
    }

    public List<HospitalResponseDto> getAllHospitals() {

        List<HospitalProfile> list = hospitalRepo.findAll();
        return list.stream().map(HospitalMapper::mapToHospitalResponseDto).collect(Collectors.toList());
    }

    public HospitalResponseDto getHospitalById(Long id){
        HospitalProfile hospitalProfile = hospitalRepo.findById(id).orElseThrow(() -> new HospitalNotFoundException("Hospital doesn't exist"));
        return HospitalMapper.mapToHospitalResponseDto(hospitalProfile);
    }

    public List<DoctorProfileResponse> getAllDoctorsOfHospital(Long hospitalId){
        HospitalProfile hospitalProfile;
        if(hospitalId == null){
            hospitalProfile = hospitalRepo.findByHospitalAdministrator(userUtils.getCurrentUser())
                    .orElseThrow(() -> new HospitalNotFoundException("Hospital profile not found for the current user"));
        }else {
            hospitalProfile = hospitalRepo.findById(hospitalId)
                    .orElseThrow(HospitalNotFoundException::new);
        }

        List<DoctorProfile> list = hospitalProfile.getDoctorProfiles();
        return list.stream().map(DoctorMapper::mapDoctorProfileResponse).toList();
    }

    public HospitalDashboardStats getHospitalDashboardStats() {

        User currentUser = userUtils.getCurrentUser();
        HospitalProfile hospitalProfile = hospitalRepo.findByHospitalAdministrator(currentUser)
                .orElseThrow(() -> new HospitalNotFoundException("Hospital profile not found for the current user"));

        int totalDoctors = hospitalProfile.getDoctorProfiles().size();
        int totalAppointments = hospitalProfile.getDoctorProfiles().stream()
                .mapToInt(doctor -> doctor.getAppointments().size())
                .sum();

        List<AppointmentStatus> statuses = List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.COMPLETED);

        List<Appointment> appointments = appointmentRepo.findByHospitalAndDateRange(
                hospitalProfile.getId(),
                java.time.LocalDateTime.now().withDayOfMonth(1),
                java.time.LocalDateTime.now(),
                statuses);
        int totalAppointmentThisMonth = appointments.size();

        int revenueThisMonth = appointments
                .stream()
                .mapToInt(appointment -> Math.toIntExact(appointment.getAmountInPaise() / 100))
                .sum();

        return new HospitalDashboardStats(totalDoctors, totalAppointments, totalAppointmentThisMonth, revenueThisMonth);
    }

    public List<DoctorProfileResponse> getAllPendingDoctorsOfHospital() {
       User currentUser = userUtils.getCurrentUser();
       HospitalProfile hospitalProfile = hospitalRepo.findByHospitalAdministrator(currentUser)
               .orElseThrow(HospitalNotFoundException::new);

       return doctorProfileRepository.findByHospitalProfileIdAndApprovalStatus(
                       hospitalProfile.getId(),
                       ApprovalStatus.WAITING_FOR_APPROVAL
               ).stream()
               .map(DoctorMapper::mapDoctorProfileResponse)
               .collect(Collectors.toList());
    }

    public HospitalResponseDto getHospitalProfile() {
        HospitalProfile hospitalProfile = hospitalRepo.findByHospitalAdministrator(userUtils.getCurrentUser())
                .orElseThrow(HospitalNotFoundException::new);
        return HospitalMapper.mapToHospitalResponseDto(hospitalProfile);
    }

    public DoctorProfileResponse verifyOrRejectDoctor(Long doctorId, boolean verify) {
        HospitalProfile hospitalProfile = hospitalRepo.findByHospitalAdministrator(userUtils.getCurrentUser())
                .orElseThrow(HospitalNotFoundException::new);

        DoctorProfile doctorProfile = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if(doctorProfile.getHospitalProfile() != hospitalProfile)
            throw new UnauthorizeAccessException("don't have right to verfiy");

        if(doctorProfile.getApprovalStatus() != ApprovalStatus.WAITING_FOR_APPROVAL)
            throw new CustomeExeption("Doctor is already approved or rejected");

        if(verify){
            doctorProfile.setVerified(true);
            doctorProfile.setApprovalStatus(ApprovalStatus.APPROVED);
        }else{
            doctorProfile.setVerified(true);
            doctorProfile.setApprovalStatus(ApprovalStatus.REJECTED);
        }
        return DoctorMapper.mapDoctorProfileResponse(doctorProfileRepository.save(doctorProfile));
    }
}
