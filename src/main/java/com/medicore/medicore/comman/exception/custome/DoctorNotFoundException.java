package com.medicore.medicore.comman.exception.custome;

public class DoctorNotFoundException extends RuntimeException
{
    public DoctorNotFoundException(){
        this("doctor not found");
    }
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
