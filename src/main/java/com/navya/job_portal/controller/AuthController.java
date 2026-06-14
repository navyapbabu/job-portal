package com.navya.job_portal.controller;

import com.navya.job_portal.dto.request.LoginRequest;
import com.navya.job_portal.dto.request.RegisterRequest;
import com.navya.job_portal.dto.response.AuthResponse;
import com.navya.job_portal.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Register and Login APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Register a new user",
            description = "Register as JOBSEEKER or EMPLOYER")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Login",
            description = "Login with email and password to get JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}