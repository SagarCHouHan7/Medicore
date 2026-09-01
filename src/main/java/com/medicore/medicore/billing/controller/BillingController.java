package com.medicore.medicore.billing.controller;

import com.medicore.medicore.billing.model.dto.BillingResponseDto;
import com.medicore.medicore.billing.service.BillingService;
import com.medicore.medicore.billing.model.status.BillingStatus;
import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/patient/bill")
    public ResponseEntity<ApiResponse<List<BillingResponseDto>>> getPatientBills() {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Patient bills retrieved successfully",
                billingService.getPatientBills()
        ));
    }

    @GetMapping("/public/bill/getByAppointmentId/{id}")
    public ResponseEntity<ApiResponse<BillingResponseDto>> getByAppointmentId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Billing retrieved successfully",
                billingService.getByAppointmentId(id)
        ));
    }

    @GetMapping("/public/bill/getByDoctor/{id}")
    public ResponseEntity<ApiResponse<List<BillingResponseDto>>> getByDoctorId(@PathVariable long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Patient bills retrieved successfully",
                billingService.getByDoctorId(id)
        ));
    }

    @GetMapping("/patient/bill/status/{status}")
    public ResponseEntity<ApiResponse<List<BillingResponseDto>>> getByDoctorId(@PathVariable("status") BillingStatus billingStatus) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Patient bills retrieved successfully",
                billingService.getPatientBillsByStatus(billingStatus)
        ));
    }




}