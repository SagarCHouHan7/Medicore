package com.medicore.medicore.account;

import com.medicore.medicore.account.dto.AuthResponse;
import com.medicore.medicore.account.dto.LoginRequest;
import com.medicore.medicore.account.dto.RegisterRequest;
import com.medicore.medicore.comman.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints for user registration and login")
@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/hello")
    ResponseEntity<ApiResponse<String>> greetings(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "welcome to medicore", "enjoy the platform"));
    }

    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details and returns an authentication token.")
    @PostMapping("/register")
    ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {

        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "User registered successfully", authResponse));


    }

    @Operation(summary = "Login a user", description = "Authenticates a user with the provided credentials and returns an authentication token.")
    @PostMapping("/login")
    ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User logged in successfully", authResponse));
    }


}
