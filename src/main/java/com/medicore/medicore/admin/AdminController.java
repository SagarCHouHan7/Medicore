package com.medicore.medicore.admin;


import com.medicore.medicore.account.AuthService;
import com.medicore.medicore.account.dto.AuthResponse;
import com.medicore.medicore.account.dto.RegisterRequest;
import com.medicore.medicore.comman.dto.ApiResponse;
import com.medicore.medicore.clinic.dto.HospitalResponseDto;
import com.medicore.medicore.clinic.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AuthService authService;
    private final HospitalService hospitalService;

    @PostMapping("/hospital")
    public ResponseEntity<ApiResponse<AuthResponse>> addHospital(@RequestBody RegisterRequest request){
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Hospital Created Successfully",
                        authService.registerDoctor(request)
                        )
                );
    }

    @GetMapping("/hospitals")
    public ResponseEntity<ApiResponse<List<HospitalResponseDto>>> getAllHospitals(){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Hospitals retrieved successfully",
                hospitalService.getAllHospitals())
        );
    }
}
