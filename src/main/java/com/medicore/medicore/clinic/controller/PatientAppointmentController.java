package com.medicore.medicore.clinic.controller;

import com.medicore.medicore.clinic.service.AppointmentService;
import com.medicore.medicore.clinic.dto.AppointmentRequest;
import com.medicore.medicore.clinic.dto.AppointmentResponse;
import com.medicore.medicore.clinic.dto.ReschedulingRequestDto;
import com.medicore.medicore.comman.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient/appointments")
public class PatientAppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<AppointmentResponse>> requestAppointment(@RequestBody AppointmentRequest appointmentRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Appointment request created successfully",
                        appointmentService.requestForAppointment(appointmentRequest)
                ));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "All appointments fetched successfully",
                        appointmentService.getAllAppointmentOfPatient()
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

    @PutMapping("/reschedule")
    public ResponseEntity<ApiResponse<AppointmentResponse>> rescheduleAppointment(@RequestBody ReschedulingRequestDto reschedulingRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Appointment rescheduled successfully",
                        appointmentService.rescheduleAppointment(reschedulingRequestDto)
                ));

    }

}