package com.navya.job_portal.controller;

import com.navya.job_portal.dto.request.LoginRequest;
import com.navya.job_portal.dto.request.RegisterRequest;
import com.navya.job_portal.dto.response.AuthResponse;
import com.navya.job_portal.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {

        @Autowired
        private AuthService authService;

        @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(
                @Valid @RequestBody RegisterRequest request) {
            return ResponseEntity.ok(authService.register(request));
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(
                @Valid @RequestBody LoginRequest request) {
            return ResponseEntity.ok(authService.login(request));
        }
    }

