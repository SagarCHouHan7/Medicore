package com.medicore.medicore.hospital;

import com.medicore.medicore.comman.dto.ApiResponse;
import com.medicore.medicore.doctor.dto.DoctorProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<HospitalResponseDto>> createHospitalProfile(@RequestBody HospitalProfileCreationDto dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Profile created successfully",
                        hospitalService.createHospitalProfile(dto))
                );
    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse<HospitalResponseDto>> createHospitalProfile(){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Profile created successfully",
                        hospitalService.getHospitalProfile())
                );
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<ApiResponse<HospitalDashboardStats>> getHospitalDashboardStats(){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Dashboard stats retrieved successfully",
                hospitalService.    getHospitalDashboardStats())
        );
    }

    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<DoctorProfileResponse>>> getAllDoctorsOfHospital(){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctors retrieved successfully",
                hospitalService.getAllDoctorsOfHospital(null))
        );
    }

    @GetMapping("/doctors/pending")
    public ResponseEntity<ApiResponse<List<DoctorProfileResponse>>> getAllPendingDoctorsOfHospital(){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Pending doctors retrieved successfully",
                hospitalService.getAllPendingDoctorsOfHospital())
        );
    }

    @PutMapping("/doctors/{doctorId}/approve")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> verifyDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctor verified successfully",
                hospitalService.verifyOrRejectDoctor(doctorId, true))
        );
    }

    @PutMapping("/doctors/{doctorId}/reject")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> rejectDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Doctor rejected successfully",
                hospitalService.verifyOrRejectDoctor(doctorId, false))
        );
    }
}
