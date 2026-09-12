package com.medicore.medicore.clinic.controller;

import com.medicore.medicore.clinic.service.DoctorService;
import com.medicore.medicore.clinic.dto.DoctorProfileRequest;
import com.medicore.medicore.clinic.dto.DoctorProfileResponse;
import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> createDoctorProfile(@RequestBody DoctorProfileRequest request) throws BadRequestException {
        // Implement the logic to create a doctor profile
        return ResponseEntity.ok(new ApiResponse<>(200, "Doctor profile created successfully", doctorService.createProfile(request)));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> getDoctorProfile() {
        // Implement the logic to get a doctor profile
        return ResponseEntity.ok(new ApiResponse<>(200, "Doctor profile retrieved successfully", doctorService.getProfile()));
    }
}
