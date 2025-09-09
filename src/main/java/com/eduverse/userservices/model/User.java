package com.eduverse.userservices.model;

import com.eduverse.userservices.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;
    
    @JsonIgnore
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;
    
    private Boolean is_verified = false;
    
    @JsonIgnore
    private String otp;
    
    @JsonIgnore
    private LocalDateTime otp_expires_at;
    
    private String provider;
    
    private String oauth_id;
    
    private String profile_image;
    
    private String phone_number;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StudentProfile student_profile;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TeacherProfile teacher_profile;
    
    // Constructors
    public User() {}
    
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public Boolean getIs_verified() { return is_verified; }
    public void setIs_verified(Boolean is_verified) { this.is_verified = is_verified; }
    
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
    
    public LocalDateTime getOtp_expires_at() { return otp_expires_at; }
    public void setOtp_expires_at(LocalDateTime otp_expires_at) { this.otp_expires_at = otp_expires_at; }
    
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    
    public String getOauth_id() { return oauth_id; }
    public void setOauth_id(String oauth_id) { this.oauth_id = oauth_id; }
    
    public String getProfile_image() { return profile_image; }
    public void setProfile_image(String profile_image) { this.profile_image = profile_image; }
    
    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
    
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
    
    public StudentProfile getStudent_profile() { return student_profile; }
    public void setStudent_profile(StudentProfile student_profile) { this.student_profile = student_profile; }
    
    public TeacherProfile getTeacher_profile() { return teacher_profile; }
    public void setTeacher_profile(TeacherProfile teacher_profile) { this.teacher_profile = teacher_profile; }
}
