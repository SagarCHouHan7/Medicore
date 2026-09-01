package com.medicore.medicore.billing.service;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.billing.repository.BillRepository;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.billing.model.entity.Bill;
import com.medicore.medicore.billing.model.dto.BillingResponseDto;
import com.medicore.medicore.comman.exception.custome.DoctorNotFoundException;
import com.medicore.medicore.comman.exception.custome.PatientNotFoundException;
import com.medicore.medicore.comman.mapper.BillingMapper;
import com.medicore.medicore.comman.utils.UserUtils;
import com.medicore.medicore.doctor.DoctorProfile;
import com.medicore.medicore.doctor.DoctorProfileRepository;
import com.medicore.medicore.hospital.HospitalRepo;
import com.medicore.medicore.patient.PatientProfile;
import com.medicore.medicore.patient.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillRepository billRepository;
    private final UserUtils userUtils;
    private final PatientProfileRepository patientProfileRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final HospitalRepo hospitalRepo;

    public List<BillingResponseDto> getPatientBills() {
        User user = userUtils.getCurrentUser();
        PatientProfile patientProfile = patientProfileRepository.findByUser(user)
                .orElseThrow(PatientNotFoundException::new);

        List<Bill> bills = billRepository.findByPaidByOrderByGeneratedAtDesc(patientProfile);

        return bills.stream().map(BillingMapper::mapToBillingResponseDto).toList();

    }

    public BillingResponseDto getByAppointmentId(Long id) {
        Bill bill = billRepository.findByAppointmentId(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        return BillingMapper.mapToBillingResponseDto(bill);
    }

    public List<BillingResponseDto> getByDoctorId(long id) {
        DoctorProfile doctorProfile = doctorProfileRepository.findById(id)
                .orElseThrow(DoctorNotFoundException::new);
        List<Bill> bills = billRepository.findByDoctorProfileOrderByGeneratedAtDesc(doctorProfile);

        return bills.stream().map(BillingMapper::mapToBillingResponseDto).toList();
    }

    public List<BillingResponseDto> getPatientBillsByStatus(BillingStatus billingStatus){
        User user = userUtils.getCurrentUser();
        PatientProfile patientProfile = patientProfileRepository.findByUser(user)
                .orElseThrow(PatientNotFoundException::new);
        List<Bill> bills = billRepository.findByPaidByAndBillingStatusOrderByGeneratedAtDesc(patientProfile, billingStatus);
        return bills.stream().map(BillingMapper::mapToBillingResponseDto).toList();
    }

}
