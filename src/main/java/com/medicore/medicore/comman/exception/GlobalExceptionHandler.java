package com.medicore.medicore.comman.exception;

import com.medicore.medicore.comman.exception.custome.*;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse response = new ErrorResponse(409, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFoundException(ProfileNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(404, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(404, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(AppointmentStatusViolationException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentStatusViolationException(AppointmentStatusViolationException ex) {
        ErrorResponse response = new ErrorResponse(400, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDoctorNotFoundException(DoctorNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(404, ex.getMessage(),currentTimeStamp());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(HospitalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHospitalNotFoundException(HospitalNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(404, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(CustomeExeption.class)
    public ResponseEntity<ErrorResponse> handleCustomeExeption(CustomeExeption ex) {
        ErrorResponse response = new ErrorResponse(400, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(UnauthorizeAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizeAccessException(UnauthorizeAccessException ex) {
        ErrorResponse response = new ErrorResponse(403, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        ErrorResponse response = new ErrorResponse(400, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse(500, ex.getMessage(), currentTimeStamp());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public  LocalDateTime currentTimeStamp(){
        return LocalDateTime.now();
    }

}
