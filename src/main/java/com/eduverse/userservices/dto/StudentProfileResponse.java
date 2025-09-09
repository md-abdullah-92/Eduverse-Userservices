package com.eduverse.userservices.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StudentProfileResponse {
    public static class UserSummary {
        private String name;
        private String role;
        private String email;
        private String phone;
        private String bio;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getBio() { return bio; }
        public void setBio(String bio) { this.bio = bio; }
    }

    private UserSummary user;
    private String profilePhoto;
    private String coverPhoto;
    private String userId;

    // extra fields if needed by UI
    private String educationLevel;
    private String institution;
    private String guardianName;
    private String guardianPhone;
    private String guardianEmail;
    private LocalDate dateOfBirth;
    private String address;
    private String bio;
    private List<QuizResultResponse> quizResults;

    private LocalDateTime createdAt;

    public UserSummary getUser() { return user; }
    public void setUser(UserSummary user) { this.user = user; }
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public String getCoverPhoto() { return coverPhoto; }
    public void setCoverPhoto(String coverPhoto) { this.coverPhoto = coverPhoto; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

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
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<QuizResultResponse> getQuizResults() { return quizResults; }
    public void setQuizResults(List<QuizResultResponse> quizResults) { this.quizResults = quizResults; }
}
