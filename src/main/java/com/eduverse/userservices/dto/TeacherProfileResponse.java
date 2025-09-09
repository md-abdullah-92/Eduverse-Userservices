package com.eduverse.userservices.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TeacherProfileResponse {
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

    // Nested DTOs to mirror Node/React expectations
    public static class QuizQuestion {
        private String question;
        private List<String> options;
        private String correctAnswer;
        private String explanation;
        private String type; // "mcq" | "cq"
        private String difficulty; // "easy" | "medium" | "hard"

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }
        public String getCorrectAnswer() { return correctAnswer; }
        public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    }

    public static class QuizSummary {
        private String id;
        private String title;
        private String description;
        private Integer duration;
        private LocalDateTime createdAt;
        private List<QuizQuestion> questions;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public List<QuizQuestion> getQuestions() { return questions; }
        public void setQuestions(List<QuizQuestion> questions) { this.questions = questions; }
    }

    public static class AssignmentSummary {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime createdAt;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    public static class StudyNoteSummary {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime createdAt;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    private UserSummary user;
    private String profilePhoto;
    private String coverPhoto;
    private String userId;

    private String education;
    private String specialization;
    private Integer experience;
    private String institution;
    private String certifications;
    private Double rating;
    private String bio;

    private LocalDateTime createdAt;

    // Additional fields expected by React hook
    private Integer totalReviews = 0;
    private Integer totalStudents = 0;
    private Integer totalSales = 0;
    private Integer totalCourses = 0;
    private Integer processingOrders = 0;
    private Integer completedOrders = 0;
    private Integer totalOrders = 0;

    // Optional collections to match Node controller response
    private List<QuizSummary> quizzes;
    private List<AssignmentSummary> assignments;
    private List<StudyNoteSummary> studyNotes;

    public UserSummary getUser() { return user; }
    public void setUser(UserSummary user) { this.user = user; }
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public String getCoverPhoto() { return coverPhoto; }
    public void setCoverPhoto(String coverPhoto) { this.coverPhoto = coverPhoto; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

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
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }
    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }
    public Integer getTotalSales() { return totalSales; }
    public void setTotalSales(Integer totalSales) { this.totalSales = totalSales; }
    public Integer getTotalCourses() { return totalCourses; }
    public void setTotalCourses(Integer totalCourses) { this.totalCourses = totalCourses; }
    public Integer getProcessingOrders() { return processingOrders; }
    public void setProcessingOrders(Integer processingOrders) { this.processingOrders = processingOrders; }
    public Integer getCompletedOrders() { return completedOrders; }
    public void setCompletedOrders(Integer completedOrders) { this.completedOrders = completedOrders; }
    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }

    public List<QuizSummary> getQuizzes() { return quizzes; }
    public void setQuizzes(List<QuizSummary> quizzes) { this.quizzes = quizzes; }
    public List<AssignmentSummary> getAssignments() { return assignments; }
    public void setAssignments(List<AssignmentSummary> assignments) { this.assignments = assignments; }
    public List<StudyNoteSummary> getStudyNotes() { return studyNotes; }
    public void setStudyNotes(List<StudyNoteSummary> studyNotes) { this.studyNotes = studyNotes; }
}
