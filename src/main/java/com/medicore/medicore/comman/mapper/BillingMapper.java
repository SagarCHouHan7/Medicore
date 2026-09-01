package com.medicore.medicore.comman.mapper;

import com.medicore.medicore.billing.model.entity.Bill;
import com.medicore.medicore.billing.model.dto.BillingResponseDto;

public class BillingMapper {

    public static BillingResponseDto mapToBillingResponseDto(Bill bill){
        return new BillingResponseDto(
                bill.getId(),
                bill.getBillingStatus(),
                (int) bill.getAmountInPaise(),
                PatientMapper.mapPatientProfileResponse(bill.getPaidBy()),
                HospitalMapper.mapToHospitalResponseDto(bill.getHospitalProfile()),
                AppointmentMapper.mapAppointmentResponse(bill.getAppointment()),
                bill.getGeneratedAt(),
                bill.getPaidAt()
        );
    }
}
