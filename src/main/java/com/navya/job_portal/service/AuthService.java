package com.navya.job_portal.service;

import com.navya.job_portal.dto.request.LoginRequest;
import com.navya.job_portal.dto.request.RegisterRequest;
import com.navya.job_portal.dto.response.AuthResponse;
import com.navya.job_portal.entity.User;
import com.navya.job_portal.repository.UserRepository;
import com.navya.job_portal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

    @Service
    public class AuthService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private AuthenticationManager authenticationManager;

        public AuthResponse register(RegisterRequest request) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered!");
            }

            User user = new User();
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            userRepository.save(user);

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            return new AuthResponse(
                    token,
                    user.getRole().name(),
                    user.getEmail(),
                    "Registration successful!"
            );
        }

        public AuthResponse login(LoginRequest request) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found!"));

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            return new AuthResponse(
                    token,
                    user.getRole().name(),
                    user.getEmail(),
                    "Login successful!"
            );
        }
    }

