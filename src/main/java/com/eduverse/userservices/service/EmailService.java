package com.eduverse.userservices.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();
    
    public String generateOTP() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
    
    public void sendOTP(String toEmail, String otp, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(toEmail);
            helper.setSubject("Your OTP for Email Verification - EduVerse");
            
            String htmlContent = String.format(
                "<h2>Welcome to EduVerse!</h2>" +
                "<p>Dear %s,</p>" +
                "<p>Your OTP for email verification is: <strong style='font-size: 24px; color: #007bff;'>%s</strong></p>" +
                "<p>This OTP will expire in 5 minutes.</p>" +
                "<p>If you didn't request this verification, please ignore this email.</p>" +
                "<br>" +
                "<p>Best regards,<br>EduVerse Team</p>",
                userName, otp
            );
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
            
            System.out.println("OTP sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
            // Fallback to simple text email
            sendSimpleOTP(toEmail, otp, userName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
    
    private void sendSimpleOTP(String toEmail, String otp, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("EduVerse - Email Verification Code");
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Welcome to EduVerse! Your email verification code is: %s\n\n" +
                "This code will expire in 5 minutes.\n\n" +
                "If you didn't request this code, please ignore this email.\n\n" +
                "Best regards,\n" +
                "EduVerse Team",
                userName, otp
            ));
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send simple OTP email: " + e.getMessage());
        }
    }
    
    public void sendPasswordResetEmail(String toEmail, String resetToken, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(toEmail);
            helper.setSubject("EduVerse - Password Reset Request");
            
            String htmlContent = String.format(
                "<h2>Password Reset Request</h2>" +
                "<p>Dear %s,</p>" +
                "<p>You have requested to reset your password for your EduVerse account.</p>" +
                "<p>Your password reset token is: <strong style='font-size: 18px; color: #dc3545;'>%s</strong></p>" +
                "<p>This token will expire in 15 minutes.</p>" +
                "<p>If you didn't request this reset, please ignore this email and contact support if you have concerns.</p>" +
                "<br>" +
                "<p>Best regards,<br>EduVerse Team</p>",
                userName, resetToken
            );
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
            
            System.out.println("Password reset email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
            // Fallback to simple text email
            sendSimplePasswordReset(toEmail, resetToken, userName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset email: " + e.getMessage());
        }
    }
    
    private void sendSimplePasswordReset(String toEmail, String resetToken, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("EduVerse - Password Reset Request");
            message.setText(String.format(
                "Dear %s,\n\n" +
                "You have requested to reset your password for your EduVerse account.\n\n" +
                "Your password reset token is: %s\n\n" +
                "This token will expire in 15 minutes.\n\n" +
                "If you didn't request this reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "EduVerse Team",
                userName, resetToken
            ));
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send simple password reset email: " + e.getMessage());
        }
    }
}
