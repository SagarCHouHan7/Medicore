package com.medicore.medicore.billing.controller;

import com.medicore.medicore.billing.model.dto.AdminDashboardResponse;
import com.medicore.medicore.billing.model.dto.DoctorDashboardResponse;
import com.medicore.medicore.billing.model.dto.HospitalDashboardResponse;
import com.medicore.medicore.billing.model.entity.Settlement;
import com.medicore.medicore.billing.model.status.SettlementStatus;
import com.medicore.medicore.billing.service.SettlementService;
import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

//    @GetMapping("/admin/settlements")
//    public ResponseEntity<ApiResponse<List<Settlement>>> getAllSettlement(){
//        return ResponseEntity.ok(
//                new ApiResponse<>(
//                        HttpStatus.OK.value(),
//                        "settlement retrived successfully",
//                        settlementService.getAllSettlements()
//                )
//        );
//    }
//
//    @GetMapping("/admin/settlements/pending")
//    public ResponseEntity<ApiResponse<List<Settlement>>> getUnsettledSettlements(){
//        return ResponseEntity.ok(
//                new ApiResponse<>(
//                        HttpStatus.OK.value(),
//                        "settlement retrived successfully",
//                        settlementService.getUnsettledSettlements(SettlementStatus.PENDING)
//                )
//        );
//    }
//
//    @PutMapping("/admin/settlements/{id}/settle")
//    public ResponseEntity<ApiResponse<Settlement>> settleSettlement(@PathVariable("id") Long id){
//        return ResponseEntity.ok(
//                new ApiResponse<>(
//                        HttpStatus.OK.value(),
//                        "settlement retrived successfully",
//                        settlementService.settleSettlement(id)
//                )
//        );
//    }

    @GetMapping("/doctor/dashboard")
    public ResponseEntity<ApiResponse<DoctorDashboardResponse>> getDoctorDashboard() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Dashboard fetched successfully",
                        settlementService.getDoctorDashboard()
                )
        );
    }

    @GetMapping("/hospital/dashboard")
    public ResponseEntity<ApiResponse<HospitalDashboardResponse>> getHospitalDashboard() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Hospital dashboard fetched successfully",
                        settlementService.getHospitalDashboard()
                )
        );
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getDashboard() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Admin dashboard fetched successfully",
                        settlementService.getAdminDashboard()
                )
        );
    }
}
