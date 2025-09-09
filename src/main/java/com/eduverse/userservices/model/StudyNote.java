package com.eduverse.userservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_notes")
public class StudyNote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "teacher_id", nullable = false)
    private Long teacher_id;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    // Constructors
    public StudyNote() {}
    
    public StudyNote(String title, String description, Long teacher_id) {
        this.title = title;
        this.description = description;
        this.teacher_id = teacher_id;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getTeacher_id() { return teacher_id; }
    public void setTeacher_id(Long teacher_id) { this.teacher_id = teacher_id; }
    
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
}
