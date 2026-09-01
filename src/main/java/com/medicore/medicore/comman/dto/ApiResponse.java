package com.medicore.medicore.comman.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
