package com.eduverse.userservices.dto;

import jakarta.validation.constraints.NotBlank;

public class StudyNoteRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    private Long teacherId; // Optional: can be provided in request body or extracted from JWT
    
    // Constructors
    public StudyNoteRequest() {}
    
    public StudyNoteRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public StudyNoteRequest(String title, String description, Long teacherId) {
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}
