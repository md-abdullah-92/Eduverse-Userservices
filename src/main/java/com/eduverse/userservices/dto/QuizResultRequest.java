package com.eduverse.userservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class QuizResultRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @Min(value = 0, message = "Marks must be non-negative")
    @NotNull(message = "Marks is required")
    private Integer marks;
    
    @Min(value = 1, message = "Full mark must be positive")
    @NotNull(message = "Full mark is required")
    private Integer fullMark;
    
    @NotNull(message = "Lesson ID is required")
    private Long lessonId;
    
    private Long courseId;
    
    // Constructors
    public QuizResultRequest() {}
    
    public QuizResultRequest(String title, Integer marks, Integer fullMark, Long lessonId) {
        this.title = title;
        this.marks = marks;
        this.fullMark = fullMark;
        this.lessonId = lessonId;
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }
    
    public Integer getFullMark() { return fullMark; }
    public void setFullMark(Integer fullMark) { this.fullMark = fullMark; }
    
    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
