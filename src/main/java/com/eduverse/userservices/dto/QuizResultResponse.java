package com.eduverse.userservices.dto;

import java.util.List;

public class QuizResultResponse {
    private String id;
    private String lessonId;
    private String courseId;
    private String title;
    private String studentId;
    private Integer fullmark;
    private Integer marks;
    private List<AnsweredQuestionResponse> answeredQuestions;
    private String createdAt;

    // Constructors
    public QuizResultResponse() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) { this.lessonId = lessonId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public Integer getFullmark() { return fullmark; }
    public void setFullmark(Integer fullmark) { this.fullmark = fullmark; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

    public List<AnsweredQuestionResponse> getAnsweredQuestions() { return answeredQuestions; }
    public void setAnsweredQuestions(List<AnsweredQuestionResponse> answeredQuestions) { this.answeredQuestions = answeredQuestions; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
