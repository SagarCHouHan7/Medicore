package com.medicore.medicore.comman.exception.custome;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(){
        this("patient not found");
    }
    public PatientNotFoundException(String message) {
        super(message);
    }
}
