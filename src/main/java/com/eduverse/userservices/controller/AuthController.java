package com.eduverse.userservices.controller;

import com.eduverse.userservices.dto.*;
import com.eduverse.userservices.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(AuthResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    
    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@Valid @RequestBody ResendOtpRequest request) {
        try {
            String message = authService.resendOtp(request.getEmail());
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(AuthResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        try {
            AuthResponse response = authService.verifyOtp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(AuthResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    // Alias for clients calling /verify-email (same as verify-otp)
    @PostMapping("/verify-email")
    public ResponseEntity<AuthResponse> verifyEmail(@Valid @RequestBody VerifyOtpRequest request) {
        try {
            AuthResponse response = authService.verifyOtp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(AuthResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String message = authService.forgotPassword(email);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String newPassword = request.get("newPassword");
            String message = authService.resetPassword(token, newPassword);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
}
