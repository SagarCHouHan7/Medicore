package com.medicore.medicore.doctor;

import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("/doctor/prescription")
    public ResponseEntity<ApiResponse<PrescriptionResponseDto>> uploadPrescription(@RequestBody PrescriptionUploadDto dto){
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Prescription uploaded successfully",
                prescriptionService.savePrescription(dto))
        );
    }

    @GetMapping("/prescription/{id}")
    public ResponseEntity<ApiResponse<PrescriptionResponseDto>> getPrescription(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Prescription retrieved successfully",
                prescriptionService.getPrescription(id))
        );
    }

    @GetMapping("/prescription/appointment/{id}")
    public ResponseEntity<ApiResponse<PrescriptionResponseDto>> getPrescriptionByAppointment(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Prescription retrieved successfully",
                prescriptionService.getPrescriptionByAppointmentId(id))
        );
    }

    @GetMapping("/prescription/patient/{id}")
    public ResponseEntity<ApiResponse<List<PrescriptionResponseDto>>> getPrescriptionByPatientId(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Prescription retrieved successfully",
                prescriptionService.getPrescriptionsByPatientId(id))
        );
    }

    @GetMapping("/prescription/doctor/{id}")
    public ResponseEntity<ApiResponse<List<PrescriptionResponseDto>>> getPrescriptionByDoctorId(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Prescription retrieved successfully",
                prescriptionService.getPrescriptionsByDoctorId(id))
        );
    }
}
