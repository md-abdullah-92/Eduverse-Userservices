package com.eduverse.userservices.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "teacher_profiles")
public class TeacherProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;
    
    @Column(nullable = false)
    private String education;
    
    @Column(nullable = false)
    private String specialization;
    
    @Min(value = 0, message = "Experience must be non-negative")
    @Column(nullable = false)
    private Integer experience;
    
    private String institution;
    
    @Column(columnDefinition = "TEXT")
    private String certifications;
    
    private Double rating;
    
    private String cover_photo;
    
    private String profile_photo;
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    // Removed bidirectional relationships for Node.js-style direct foreign key approach
    // Use repository queries to fetch related entities when needed
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    // Constructors
    public TeacherProfile() {}
    
    public TeacherProfile(User user, String education, String specialization, Integer experience) {
        this.user = user;
        this.education = education;
        this.specialization = specialization;
        this.experience = experience;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }
    
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    
    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public String getCover_photo() { return cover_photo; }
    public void setCover_photo(String cover_photo) { this.cover_photo = cover_photo; }
    
    public String getProfile_photo() { return profile_photo; }
    public void setProfile_photo(String profile_photo) { this.profile_photo = profile_photo; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    // Removed collection getter/setter methods for Node.js-style approach
    
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
}
