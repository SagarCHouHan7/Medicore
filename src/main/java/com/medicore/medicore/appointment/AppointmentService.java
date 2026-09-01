package com.medicore.medicore.appointment;

import com.medicore.medicore.account.entity.Role;
import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.appointment.dto.AppointmentRequest;
import com.medicore.medicore.appointment.dto.AppointmentResponse;
import com.medicore.medicore.appointment.dto.ReschedulingRequestDto;
import com.medicore.medicore.billing.model.entity.Bill;
import com.medicore.medicore.billing.repository.BillRepository;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.doctor.DoctorProfile;
import com.medicore.medicore.doctor.DoctorProfileRepository;
import com.medicore.medicore.comman.exception.custome.AppointmentNotFoundException;
import com.medicore.medicore.comman.exception.custome.DoctorNotFoundException;
import com.medicore.medicore.comman.exception.custome.PatientNotFoundException;
import com.medicore.medicore.comman.mapper.AppointmentMapper;
import com.medicore.medicore.patient.PatientProfile;
import com.medicore.medicore.patient.PatientProfileRepository;
import com.medicore.medicore.comman.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final PatientProfileRepository patientProfileRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final UserUtils userUtils;
    private final BillRepository billRepository;

    public AppointmentResponse requestForAppointment(AppointmentRequest request){
        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(request.appointmentDateTime());
        appointment.setReason(request.reason());
        appointment.setAppointmentStatus(AppointmentStatus.REQUESTED);

        User user = userUtils.getCurrentUser();

        PatientProfile patientProfile = patientProfileRepository.findByUser(user)
                .orElseThrow(PatientNotFoundException::new);
        DoctorProfile doctorProfile = doctorProfileRepository.findById(request.doctorId())
                .orElseThrow(DoctorNotFoundException::new);

        appointment.setAmountInPaise((long) (doctorProfile.getConsultationFees()*100));
        appointment.setDoctor(doctorProfile);
        appointment.setPatient(patientProfile);
        Appointment savedAppointment = appointmentRepo.save(appointment);

       return AppointmentMapper.mapAppointmentResponse(savedAppointment);

    }

    public List<AppointmentResponse> getAllAppointmentOfPatient(){
        User user = userUtils.getCurrentUser();
        PatientProfile patientProfile = patientProfileRepository.findByUser(user)
                .orElseThrow(PatientNotFoundException::new);
        List<Appointment> list = appointmentRepo.findByPatient(patientProfile);

        return list.stream().map(AppointmentMapper::mapAppointmentResponse).toList();
    }

    public  List<AppointmentResponse> getAllAppointmentOfDoctor(){
        User user = userUtils.getCurrentUser();
        DoctorProfile doctorProfile = doctorProfileRepository.findByUser(user)
                .orElseThrow(DoctorNotFoundException::new);
        List<Appointment> list = appointmentRepo.findByDoctor(doctorProfile);

        return list.stream().map(AppointmentMapper::mapAppointmentResponse).toList();
    }


    public AppointmentResponse getAppointmentById(Long id){
        log.info("inside getAppointmentById method of AppointmentService");
        User user = userUtils.getCurrentUser();
        if(user.getRole() == Role.ROLE_DOCTOR){
            log.info("doctor");
            DoctorProfile doctorProfile = doctorProfileRepository.findByUser(user)
                    .orElseThrow(DoctorNotFoundException::new);
            Appointment appointment = appointmentRepo.findByIdAndDoctor(id, doctorProfile)
                    .orElseThrow(AppointmentNotFoundException::new);
            if(!appointment.getDoctor().equals(doctorProfile)){
                throw new AppointmentNotFoundException();
            }
            return AppointmentMapper.mapAppointmentResponse(appointment);
        }
        if(user.getRole() == Role.ROLE_PATIENT){
            log.info("patient");
            PatientProfile patientProfile = patientProfileRepository.findByUser(user)
                    .orElseThrow(PatientNotFoundException::new);
            Appointment appointment = appointmentRepo.findByIdAndPatient(id, patientProfile)
                    .orElseThrow(AppointmentNotFoundException::new);
            return AppointmentMapper.mapAppointmentResponse(appointment);
        }
        throw new AppointmentNotFoundException();
    }

    public AppointmentResponse markCancelOrConfirm(AppointmentStatus status, Long appointmentId){

        log.info("inside markCancelOrConfirm method of AppointmentService");
        Appointment appointment = getDoctorsAppointment(appointmentId);

        if(appointment.getAppointmentStatus() == AppointmentStatus.REQUESTED && status == AppointmentStatus.CANCELED){
            appointment.setAppointmentStatus(status);
            Appointment savedAppointment = appointmentRepo.save(appointment);
            return AppointmentMapper.mapAppointmentResponse(savedAppointment);

        }else if(appointment.getAppointmentStatus() == AppointmentStatus.REQUESTED && status == AppointmentStatus.CONFIRMED){
            appointment.setAppointmentStatus(AppointmentStatus.PAYMENT_PENDING);
            Appointment savedAppointment = appointmentRepo.save(appointment);
            Bill bill = new Bill();
            bill.setAppointment(savedAppointment);
            bill.setAmountInPaise(savedAppointment.getAmountInPaise());
            bill.setBillingStatus(BillingStatus.PENDING);
            bill.setDoctorProfile(savedAppointment.getDoctor());
            bill.setPaidBy(savedAppointment.getPatient());
            bill.setHospitalProfile(savedAppointment.getDoctor().getHospitalProfile());
            billRepository.save(bill);
            return AppointmentMapper.mapAppointmentResponse(savedAppointment);
        }else{
            throw new IllegalArgumentException("Invalid status transition");
        }
    }

    public AppointmentResponse markAppointmentAsCompleted(Long appointmentId){
        Appointment appointment = getDoctorsAppointment(appointmentId);

        if(appointment.getAppointmentStatus() == AppointmentStatus.SCHEDULED){
            appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
            Appointment savedAppointment = appointmentRepo.save(appointment);
            return AppointmentMapper.mapAppointmentResponse(savedAppointment);
        }else{
            throw new IllegalArgumentException("Invalid status transition");
        }
    }

    private Appointment getDoctorsAppointment(Long appointmentId){
        User user = userUtils.getCurrentUser();
        DoctorProfile doctorProfile = doctorProfileRepository.findByUser(user)
                .orElseThrow(() -> new DoctorNotFoundException("you don't have right to mark status"));

        return appointmentRepo.findByIdAndDoctor(appointmentId, doctorProfile)
                .orElseThrow(AppointmentNotFoundException::new);
    }

    public AppointmentResponse rescheduleAppointment(ReschedulingRequestDto reschedulingRequestDto) {
        Appointment appointment = appointmentRepo.findById(reschedulingRequestDto.id())
                .orElseThrow(AppointmentNotFoundException::new);
        appointment.setAppointmentDateTime(reschedulingRequestDto.newTime());
        Appointment savedAppointment = appointmentRepo.save(appointment);
        return AppointmentMapper.mapAppointmentResponse(savedAppointment);
    }


}
