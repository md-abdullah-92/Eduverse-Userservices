package com.eduverse.userservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

public class TeacherProfileRequest {
    
    @NotBlank(message = "Education is required")
    private String education;
    
    @NotBlank(message = "Specialization is required")
    private String specialization;
    
    @Min(value = 0, message = "Experience must be non-negative")
    private Integer experience;
    
    private String institution;
    private String certifications;
    private Double rating;
    private String coverPhoto;
    private String profilePhoto;
    private String bio;
    
    // Constructors
    public TeacherProfileRequest() {}
    
    // Getters and Setters
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
    
    public String getCoverPhoto() { return coverPhoto; }
    public void setCoverPhoto(String coverPhoto) { this.coverPhoto = coverPhoto; }
    
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
