package com.eduverse.userservices.dto;

import java.util.List;

public class AnsweredQuestionResponse {
    private String id;
    private String question;
    private String correctAnswer;
    private String userAnswer;
    private List<String> options;
    private String explanation;
    private String difficulty;
    private String type;
    private String quizId;
    private String quizResultId;

    // Constructors
    public AnsweredQuestionResponse() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }

    public String getQuizResultId() { return quizResultId; }
    public void setQuizResultId(String quizResultId) { this.quizResultId = quizResultId; }
}
