package com.medicore.medicore.patient;

import com.medicore.medicore.comman.dto.ApiResponse;
import com.medicore.medicore.patient.dto.PatientProfileRequest;
import com.medicore.medicore.patient.dto.PatientProfileResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<PatientProfileResponse>> createPatientProfile(@RequestBody PatientProfileRequest request) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Patient profile created successfully", patientService.createProfile(request)));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<PatientProfileResponse>> getPatientProfile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Patient profile retrieved successfully", patientService.getProfile()));
    }

}
