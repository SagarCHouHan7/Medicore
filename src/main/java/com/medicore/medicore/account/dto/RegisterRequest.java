package com.medicore.medicore.account.dto;

import com.medicore.medicore.account.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(

        @NotBlank(message = "full name is required")
        String fullName,

        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
