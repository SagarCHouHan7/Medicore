package com.medicore.medicore.comman.exception.custome;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(){
        this("Appointment not found");
    }
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
