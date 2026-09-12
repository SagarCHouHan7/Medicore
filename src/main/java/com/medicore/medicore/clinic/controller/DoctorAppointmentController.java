package com.medicore.medicore.clinic.controller;

import com.medicore.medicore.clinic.service.AppointmentService;
import com.medicore.medicore.clinic.model.AppointmentStatus;
import com.medicore.medicore.clinic.dto.AppointmentResponse;
import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor/appointments")
public class DoctorAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "All appointments fetched successfully",
                        appointmentService.getAllAppointmentOfDoctor()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Appointment fetched successfully",
                        appointmentService.getAppointmentById(id)
                ));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> markStatus(@PathVariable("id") Long appointmentId ,@PathVariable("status") AppointmentStatus status){
        if(status == AppointmentStatus.COMPLETED){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            HttpStatus.OK.value(),
                            "Appointment marked as completed successfully",
                            appointmentService.markAppointmentAsCompleted(appointmentId)
                    ));
        }
        if(status == AppointmentStatus.CONFIRMED || status == AppointmentStatus.CANCELED){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            HttpStatus.OK.value(),
                            "Appointment status updated successfully",
                            appointmentService.markCancelOrConfirm(status, appointmentId)
                    ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid status",
                        null
                ));
    }


}
