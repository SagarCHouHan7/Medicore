package com.medicore.medicore.comman.exception.custome;

public class HospitalNotFoundException extends RuntimeException {

    public HospitalNotFoundException() {

        super("Hospital Not created yet, contact to admin");
    }
    public HospitalNotFoundException(String message) {
        super(message);
    }
}
