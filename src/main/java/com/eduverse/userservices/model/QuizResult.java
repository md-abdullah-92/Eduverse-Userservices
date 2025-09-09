package com.eduverse.userservices.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "quiz_results")
public class QuizResult {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @Min(value = 0, message = "Marks must be non-negative")
    @Column(nullable = false)
    private Integer marks;
    
    @Min(value = 1, message = "Full mark must be positive")
    @Column(nullable = false)
    private Integer full_mark;
    
    @Column(name = "student_id", nullable = false)
    private Long student_id;
    
    @OneToMany(mappedBy = "quiz_result_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnsweredQuestion> answered_questions;

    
    @Column(nullable = false)
    private Long lesson_id;
    
    private Long course_id;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    // Constructors
    public QuizResult() {}
    
    public QuizResult(String title, Integer marks, Integer full_mark, Long student_id, Long lesson_id) {
        this.title = title;
        this.marks = marks;
        this.full_mark = full_mark;
        this.student_id = student_id;
        this.lesson_id = lesson_id;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }
    
    public Integer getFull_mark() { return full_mark; }
    public void setFull_mark(Integer full_mark) { this.full_mark = full_mark; }
    
    public Long getStudent_id() { return student_id; }
    public void setStudent_id(Long student_id) { this.student_id = student_id; }
    

    
    public Long getLesson_id() { return lesson_id; }
    public void setLesson_id(Long lesson_id) { this.lesson_id = lesson_id; }
    
    public Long getCourse_id() { return course_id; }
    public void setCourse_id(Long course_id) { this.course_id = course_id; }
    
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
    
    public List<AnsweredQuestion> getAnswered_questions() { return answered_questions; }
    public void setAnswered_questions(List<AnsweredQuestion> answered_questions) { this.answered_questions = answered_questions; }
}
