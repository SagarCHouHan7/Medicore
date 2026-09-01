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
@RequestMapping("/public")
public class PublicHospitalController {

    private final HospitalService hospitalService;

    @GetMapping("/hospital/all")
    public ResponseEntity<ApiResponse<List<HospitalResponseDto>>> getAllHospitals(){
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "hospitals retrieved successfully",
                hospitalService.getAllHospitals()));
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<ApiResponse<HospitalResponseDto>> getHospitalById(@PathVariable Long id){
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "hospital retrived successfully",
                        hospitalService.getHospitalById(id)
                )
        );
    }

    @GetMapping("/hospital/{id}/doctors")
    public ResponseEntity<ApiResponse<List<DoctorProfileResponse>>> getAllDoctorsOfHospital(@PathVariable("id") Long hospitalId){
        return ResponseEntity.ok(
                new ApiResponse<>(200,"retrived successfully", hospitalService.getAllDoctorsOfHospital(hospitalId))
        );
    }



}
