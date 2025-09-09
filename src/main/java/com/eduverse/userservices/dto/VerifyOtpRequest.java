package com.eduverse.userservices.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VerifyOtpRequest {
    
    @NotBlank(message = "OTP is required")
    @Size(min = 6, max = 6, message = "OTP must be 6 digits")
    private String otp;

    // Optional but recommended to avoid OTP collisions
    @Email(message = "Invalid email format")
    private String email;
    
    // Constructors
    public VerifyOtpRequest() {}
    
    public VerifyOtpRequest(String otp) {
        this.otp = otp;
    }

    public VerifyOtpRequest(String otp, String email) {
        this.otp = otp;
        this.email = email;
    }
    
    // Getters and Setters
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
