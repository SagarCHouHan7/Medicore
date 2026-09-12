package com.medicore.medicore.clinic.controller;

import com.medicore.medicore.clinic.service.DoctorService;
import com.medicore.medicore.clinic.dto.DoctorProfileResponse;
import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicDoctorController {

    private final DoctorService doctorService;


    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<DoctorProfileResponse>>> getDoctorProfile() {
        List<DoctorProfileResponse> doctorProfileResponse = doctorService.getAllDoctors();
        return ResponseEntity.ok(new ApiResponse<>(200, "Doctor profile retrieved successfully", doctorProfileResponse));
    }


    @GetMapping("/doctors/{id}")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> getDoctorProfileById(@PathVariable Long id) {
        DoctorProfileResponse doctorProfileResponse = doctorService.getDoctorById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Doctor profile retrieved successfully", doctorProfileResponse));
    }

}
