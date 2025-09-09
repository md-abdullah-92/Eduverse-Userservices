package com.eduverse.userservices.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class QuizRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;
    
    private Long teacherId; // Optional: can be provided in request body or extracted from JWT
    
    // Constructors
    public QuizRequest() {}
    
    public QuizRequest(String title, String description, Integer duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
    }
    
    public QuizRequest(String title, String description, Integer duration, Long teacherId) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.teacherId = teacherId;
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}
