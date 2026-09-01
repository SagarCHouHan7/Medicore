package com.medicore.medicore.comman.exception.custome;

public class CustomeExeption extends RuntimeException {
    public CustomeExeption(){
        this("unexpected error");
    }
    public CustomeExeption(String message) {
        super(message);
    }
}
