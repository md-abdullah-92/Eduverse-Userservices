package com.eduverse.userservices.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduverse.userservices.dto.AuthResponse;
import com.eduverse.userservices.dto.LoginRequest;
import com.eduverse.userservices.dto.RegisterRequest;
import com.eduverse.userservices.dto.VerifyOtpRequest;
import com.eduverse.userservices.exception.BadRequestException;
import com.eduverse.userservices.exception.UserNotFoundException;
import com.eduverse.userservices.model.StudentProfile;
import com.eduverse.userservices.model.TeacherProfile;
import com.eduverse.userservices.model.User;
import com.eduverse.userservices.model.enums.Role;
import com.eduverse.userservices.repository.StudentProfileRepository;
import com.eduverse.userservices.repository.TeacherProfileRepository;
import com.eduverse.userservices.repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StudentProfileRepository studentProfileRepository;
    
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
    
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        // Validate role
        if (request.getRole() != null && 
            !request.getRole().equals("STUDENT") && 
            !request.getRole().equals("TEACHER")) {
            throw new RuntimeException("Invalid role! Role must be either STUDENT or TEACHER");
        }
        
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.STUDENT);
        user.setIs_verified(false);
        
        // Generate OTP
        String otp = emailService.generateOTP();
        user.setOtp(otp);
        user.setOtp_expires_at(LocalDateTime.now().plusMinutes(5));
        
        User savedUser = userRepository.save(user);
        
        // Send OTP email
        try {
            emailService.sendOTP(savedUser.getEmail(), otp, savedUser.getName());
        } catch (Exception e) {
            // Log error but don't fail registration
            System.err.println("Failed to send OTP email: " + e.getMessage());
        }
        
        String jwtToken = jwtService.generateToken(savedUser);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .user(savedUser)
                .message("Registration successful! Please verify your email with the OTP sent.")
                .build();
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String jwtToken = jwtService.generateToken(user);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .user(user)
                .message("Login successful!")
                .build();
    }
    
    public AuthResponse verifyOtp(VerifyOtpRequest request) {
        // Step 1: Find user by email if provided, otherwise search by OTP only
        User user;
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            // First check if user exists
            user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
        } else {
            // Find user by OTP only (less secure but supported for backward compatibility)
            user = userRepository.findByOtp(request.getOtp())
                    .orElseThrow(() -> new UserNotFoundException("User not found!"));
        }
        
        // Step 2: Check if user is already verified
        if (Boolean.TRUE.equals(user.getIs_verified())) {
            throw new BadRequestException("Email already verified!");
        }
        
        // Step 3: Validate OTP correctness
        if (!request.getOtp().equals(user.getOtp())) {
            throw new BadRequestException("Invalid OTP!");
        }
        
        // Step 4: Check OTP expiration
        if (user.getOtp_expires_at() == null || user.getOtp_expires_at().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP has expired!");
        }
        
        // Step 5: Update user verification status and clear OTP fields
        user.setIs_verified(true);
        user.setOtp(null);
        user.setOtp_expires_at(null);
        
        User savedUser = userRepository.save(user);
        
        // Step 6: Create profile after verification (similar to Node.js backend)
        try {
            if (savedUser.getRole() == Role.STUDENT) {
                // Create student profile if it doesn't exist
                if (!studentProfileRepository.existsByUserId(savedUser.getId())) {
                    StudentProfile studentProfile = new StudentProfile();
                    studentProfile.setUser(savedUser);
                    studentProfile.setEducation_level("Not set");
                    studentProfile.setInstitution("Not set");
                    studentProfile.setGuardian_name("Not set");
                    studentProfile.setGuardian_phone("Not set");
                    studentProfileRepository.save(studentProfile);
                }
            } else if (savedUser.getRole() == Role.TEACHER) {
                // Create teacher profile if it doesn't exist
                if (!teacherProfileRepository.existsByUserId(savedUser.getId())) {
                    TeacherProfile teacherProfile = new TeacherProfile();
                    teacherProfile.setUser(savedUser);
                    teacherProfile.setEducation("Not set");
                    teacherProfile.setSpecialization("Not set");
                    teacherProfile.setExperience(0);
                    teacherProfileRepository.save(teacherProfile);
                }
            }
        } catch (Exception e) {
            // Log the error but don't fail the verification process
            System.err.println("Failed to create profile for user " + savedUser.getId() + ": " + e.getMessage());
        }
        
        // Step 7: Generate JWT token and return response
        String jwtToken = jwtService.generateToken(savedUser);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .user(savedUser)
                .message("Email verified successfully!")
                .build();
    }
    
    /**
     * Resend a fresh OTP to the user's email if the user exists and is not yet verified.
     */
    public String resendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with this email"));
        
        if (Boolean.TRUE.equals(user.getIs_verified())) {
            throw new BadRequestException("Email is already verified");
        }
        
        String otp = emailService.generateOTP();
        user.setOtp(otp);
        user.setOtp_expires_at(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        
        try {
            emailService.sendOTP(user.getEmail(), otp, user.getName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
        
        return "OTP resent successfully";
    }
    
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));
        
        // Generate reset token (using OTP field for simplicity)
        String resetToken = emailService.generateOTP();
        user.setOtp(resetToken);
        user.setOtp_expires_at(LocalDateTime.now().plusMinutes(15));
        
        userRepository.save(user);
        
        // Send password reset email
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), resetToken, user.getName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset email: " + e.getMessage());
        }
        
        return "Password reset token sent to your email";
    }
    
    public String resetPassword(String token, String newPassword) {
        User user = userRepository.findByOtpAndOtpExpiresAtAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        user.setOtp_expires_at(null);
        
        userRepository.save(user);
        
        return "Password reset successfully";
    }
}
