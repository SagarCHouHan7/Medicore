package com.medicore.medicore.comman.mapper;

import com.medicore.medicore.clinic.model.Appointment;
import com.medicore.medicore.clinic.dto.AppointmentResponse;
import com.medicore.medicore.clinic.model.HospitalProfile;

public class AppointmentMapper {

    public static AppointmentResponse mapAppointmentResponse(Appointment appointment){
        HospitalProfile hospitalProfile = appointment.getDoctor().getHospitalProfile();
        String address = String.join(" ",
                hospitalProfile.getAddress(),
                hospitalProfile.getCity(),
                hospitalProfile.getState(),
                hospitalProfile.getZipCode()
        );

        return new AppointmentResponse(
                appointment.getId(),
                PatientMapper.mapPatientProfileResponse(appointment.getPatient()),
                DoctorMapper.mapDoctorProfileResponse(appointment.getDoctor()),
                appointment.getAppointmentDateTime(),
                appointment.getReason(),
                appointment.getAppointmentStatus(),
                appointment.getCreatedTime(),
                Math.toIntExact(appointment.getAmountInPaise()),
                appointment.getCurrency(),
                hospitalProfile.getHospitalName(),
                address
        );
    }
}
