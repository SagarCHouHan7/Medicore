package com.medicore.medicore.account.dto;

import com.medicore.medicore.account.entity.Role;

public record AuthResponse(
        String token,
        Long userId,
        String fullName,
        String email,
        Role role
) {
}
