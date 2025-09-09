package com.eduverse.userservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(nullable = false)
    private Integer duration;
    
    @Column(name = "teacher_id", nullable = false)
    private Long teacher_id;
    

    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    // Constructors
    public Quiz() {}
    
    public Quiz(String title, String description, Integer duration, Long teacher_id) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.teacher_id = teacher_id;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Long getTeacher_id() { return teacher_id; }
    public void setTeacher_id(Long teacher_id) { this.teacher_id = teacher_id; }
    

    
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
}
