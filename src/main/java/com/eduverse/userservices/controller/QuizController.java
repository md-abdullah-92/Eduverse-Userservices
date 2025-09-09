package com.eduverse.userservices.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduverse.userservices.dto.QuizRequest;
import com.eduverse.userservices.model.Quiz;
import com.eduverse.userservices.service.JwtService;
import com.eduverse.userservices.service.QuizService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*")
public class QuizController {
    
    @Autowired
    private QuizService quizService;
    
    @Autowired
    private JwtService jwtService;
    
    private Long getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                return jwtService.extractUserId(token);
            } catch (Exception e) {
                // Token is invalid, but we'll allow the operation without user context
                return null;
            }
        }
        return null; // No token provided, but allow operation
    }
    
    @PostMapping
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizRequest request, HttpServletRequest httpRequest) {
        try {
            // Get teacherId from request body if JWT is not available
            Long teacherId = getCurrentUserId(httpRequest);
            if (teacherId == null && request.getTeacherId() != null) {
                teacherId = request.getTeacherId();
            }
            if (teacherId == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Teacher ID is required"));
            }
            
            Quiz quiz = quizService.createQuiz(teacherId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Quiz created successfully",
                "quiz", quiz
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable String id) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        if (quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/teacher/me")
    public ResponseEntity<List<Quiz>> getMyQuizzes(HttpServletRequest request) {
        try {
            Long teacherId = getCurrentUserId(request);
            List<Quiz> quizzes = quizService.getQuizzesByTeacher(teacherId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable String id, @Valid @RequestBody QuizRequest request) {
        try {
            Quiz quiz = quizService.updateQuiz(id, request);
            return ResponseEntity.ok(Map.of(
                "message", "Quiz updated successfully",
                "quiz", quiz
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable String id) {
        try {
            quizService.deleteQuiz(id);
            return ResponseEntity.ok(Map.of("message", "Quiz deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Quiz>> searchQuizzes(@RequestParam String title) {
        List<Quiz> quizzes = quizService.searchQuizzes(title);
        return ResponseEntity.ok(quizzes);
    }
}
