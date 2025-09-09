package com.eduverse.userservices.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;
    
    @NotBlank(message = "Education level is required")
    @Column(nullable = false)
    private String education_level;
    
    @NotBlank(message = "Institution is required")
    @Column(nullable = false)
    private String institution;
    
    @Column(nullable = false)
    private String guardian_name;
    
    @Column(nullable = false)
    private String guardian_phone;
    
    private String guardian_email;
    
    private LocalDate date_of_birth;
    
    private String address;
    
    private String cover_photo;
    
    private String profile_photo;
    
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
    
    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizResult> quiz_results;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    // Constructors
    public StudentProfile() {}
    
    public StudentProfile(User user, String education_level, String institution, 
                          String guardian_name, String guardian_phone) {
        this.user = user;
        this.education_level = education_level;
        this.institution = institution;
        this.guardian_name = guardian_name;
        this.guardian_phone = guardian_phone;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getEducation_level() { return education_level; }
    public void setEducation_level(String education_level) { this.education_level = education_level; }
    
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    
    public String getGuardian_name() { return guardian_name; }
    public void setGuardian_name(String guardian_name) { this.guardian_name = guardian_name; }
    
    public String getGuardian_phone() { return guardian_phone; }
    public void setGuardian_phone(String guardian_phone) { this.guardian_phone = guardian_phone; }
    
    public String getGuardian_email() { return guardian_email; }
    public void setGuardian_email(String guardian_email) { this.guardian_email = guardian_email; }
    
    public LocalDate getDate_of_birth() { return date_of_birth; }
    public void setDate_of_birth(LocalDate date_of_birth) { this.date_of_birth = date_of_birth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCover_photo() { return cover_photo; }
    public void setCover_photo(String cover_photo) { this.cover_photo = cover_photo; }
    
    public String getProfile_photo() { return profile_photo; }
    public void setProfile_photo(String profile_photo) { this.profile_photo = profile_photo; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public List<QuizResult> getQuiz_results() { return quiz_results; }
    public void setQuiz_results(List<QuizResult> quiz_results) { this.quiz_results = quiz_results; }
    
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
}
