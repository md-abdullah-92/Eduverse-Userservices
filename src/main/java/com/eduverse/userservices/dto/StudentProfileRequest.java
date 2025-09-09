package com.eduverse.userservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public class StudentProfileRequest {
    
    @NotBlank(message = "Education level is required")
    private String educationLevel;
    
    @NotBlank(message = "Institution is required")
    private String institution;
    
    @NotBlank(message = "Guardian name is required")
    private String guardianName;
    
    @NotBlank(message = "Guardian phone is required")
    private String guardianPhone;
    
    @Email(message = "Guardian email should be valid")
    private String guardianEmail;
    
    private LocalDate dateOfBirth;
    private String address;
    private String coverPhoto;
    private String profilePhoto;
    private String bio;
    
    // Constructors
    public StudentProfileRequest() {}
    
    // Getters and Setters
    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    
    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }
    
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    
    public String getGuardianEmail() { return guardianEmail; }
    public void setGuardianEmail(String guardianEmail) { this.guardianEmail = guardianEmail; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCoverPhoto() { return coverPhoto; }
    public void setCoverPhoto(String coverPhoto) { this.coverPhoto = coverPhoto; }
    
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
