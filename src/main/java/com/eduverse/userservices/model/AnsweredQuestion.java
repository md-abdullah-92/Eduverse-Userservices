package com.eduverse.userservices.model;

import com.eduverse.userservices.model.enums.DifficultyLevel;
import com.eduverse.userservices.model.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "answered_questions")
public class AnsweredQuestion {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @NotBlank(message = "Question text is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;
    
    @Column(columnDefinition = "TEXT")
    private String correct_answer;
    
    @Column(columnDefinition = "TEXT")
    private String user_answer;
    
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> options;
    
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficulty;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;
    
    private String quiz_id;
    
    @Column(name = "quiz_result_id", nullable = false)
    private String quiz_result_id;
    
    // Constructors
    public AnsweredQuestion() {}
    
    public AnsweredQuestion(String question, String correct_answer, String user_answer, 
                           DifficultyLevel difficulty, QuestionType type, String quiz_result_id) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.user_answer = user_answer;
        this.difficulty = difficulty;
        this.type = type;
        this.quiz_result_id = quiz_result_id;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    
    public String getCorrect_answer() { return correct_answer; }
    public void setCorrect_answer(String correct_answer) { this.correct_answer = correct_answer; }
    
    public String getUser_answer() { return user_answer; }
    public void setUser_answer(String user_answer) { this.user_answer = user_answer; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
    
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    
    public DifficultyLevel getDifficulty() { return difficulty; }
    public void setDifficulty(DifficultyLevel difficulty) { this.difficulty = difficulty; }
    
    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }
    
    public String getQuiz_id() { return quiz_id; }
    public void setQuiz_id(String quiz_id) { this.quiz_id = quiz_id; }
    
    public String getQuiz_result_id() { return quiz_result_id; }
    public void setQuiz_result_id(String quiz_result_id) { this.quiz_result_id = quiz_result_id; }
}
