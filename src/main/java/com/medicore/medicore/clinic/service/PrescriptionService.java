package com.medicore.medicore.clinic.service;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.clinic.model.DoctorProfile;
import com.medicore.medicore.clinic.repository.DoctorProfileRepository;
import com.medicore.medicore.clinic.model.Prescription;
import com.medicore.medicore.clinic.model.PrescriptionMedicine;
import com.medicore.medicore.clinic.dto.PrescriptionResponseDto;
import com.medicore.medicore.clinic.dto.PrescriptionUploadDto;
import com.medicore.medicore.clinic.model.Appointment;
import com.medicore.medicore.clinic.repository.AppointmentRepo;
import com.medicore.medicore.clinic.repository.PrescriptionRepository;
import com.medicore.medicore.comman.mapper.PrescriptionMapper;
import com.medicore.medicore.comman.utils.UserUtils;
import com.medicore.medicore.clinic.model.PatientProfile;
import com.medicore.medicore.clinic.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final AppointmentRepo appointmentRepo;
    private final UserUtils userUtils;
    
    public PrescriptionResponseDto savePrescription(PrescriptionUploadDto dto) {
        User user = userUtils.getCurrentUser();
        DoctorProfile doctorProfile = doctorProfileRepository.findByUser(user)
                .orElseThrow(()->new RuntimeException("Doctor profile not found"));

        Appointment appointment = appointmentRepo.findById(dto.appointmentId())
                .orElseThrow(()->new RuntimeException("Appointment not found"));

        PatientProfile patientProfile = appointment.getPatient();

        Prescription prescription = prescriptionRepository
                .findByAppointment_Id(dto.appointmentId())
                .orElse(new Prescription());

        prescription.setAppointment(appointment);
        prescription.setDoctorProfile(doctorProfile);
        prescription.setPatientProfile(patientProfile);
        prescription.setFollowUpDate(dto.followUpDate());

        List<PrescriptionMedicine> medicines = dto.prescriptionMedicines()
                .stream()
                .map(medicine -> {
                    medicine.setPrescription(prescription);
                    return medicine;
                })
                .collect(Collectors.toList());

        if (prescription.getPrescriptionMedicines() == null) {
            prescription.setPrescriptionMedicines(new ArrayList<>());
        }

        prescription.getPrescriptionMedicines().clear();

        medicines.forEach(medicine -> {
            medicine.setPrescription(prescription);
            prescription.getPrescriptionMedicines().add(medicine);
        });
 log.info("saving ");
        Prescription save = prescriptionRepository.save(prescription);
        log.info("saved " );

        return PrescriptionMapper.mapPrescriptionToResponseDto(save);
    }

    public PrescriptionResponseDto getPrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Prescription not found"));
        return PrescriptionMapper.mapPrescriptionToResponseDto(prescription);
    }

    public PrescriptionResponseDto getPrescriptionByAppointmentId(Long appointmentId) {
        Prescription prescription = prescriptionRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new RuntimeException("Prescription not found for the given appointment ID"));
        return PrescriptionMapper.mapPrescriptionToResponseDto(prescription);
    }

    public List<PrescriptionResponseDto> getPrescriptionsByPatientId(Long patientId) {
        List<Prescription> prescriptions = prescriptionRepository.findByPatientProfile_Id(patientId);
        return prescriptions.stream()
                .map(PrescriptionMapper::mapPrescriptionToResponseDto)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponseDto> getPrescriptionsByDoctorId(Long doctorId) {
        List<Prescription> prescriptions = prescriptionRepository.findByDoctorProfile_Id(doctorId);
        return prescriptions.stream()
                .map(PrescriptionMapper::mapPrescriptionToResponseDto)
                .collect(Collectors.toList());
    }
}
