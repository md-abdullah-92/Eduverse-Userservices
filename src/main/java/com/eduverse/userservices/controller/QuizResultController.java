package com.eduverse.userservices.controller;

import com.eduverse.userservices.dto.QuizResultRequest;
import com.eduverse.userservices.model.QuizResult;
import com.eduverse.userservices.service.JwtService;
import com.eduverse.userservices.service.QuizResultService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/result")
@CrossOrigin(origins = "*")
public class QuizResultController {
    
    @Autowired
    private QuizResultService quizResultService;
    
    @Autowired
    private JwtService jwtService;
    
    private Long getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtService.extractUserId(token);
        }
        throw new RuntimeException("No valid token found");
    }
    
    @PostMapping
    public ResponseEntity<?> createQuizResult(@Valid @RequestBody QuizResultRequest request, HttpServletRequest httpRequest) {
        try {
            Long studentId = getCurrentUserId(httpRequest);
            QuizResult quizResult = quizResultService.createQuizResult(studentId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Quiz result created successfully",
                "quizResult", quizResult
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<QuizResult>> getAllQuizResults() {
        List<QuizResult> quizResults = quizResultService.getAllQuizResults();
        return ResponseEntity.ok(quizResults);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizResultById(@PathVariable String id) {
        Optional<QuizResult> quizResult = quizResultService.getQuizResultById(id);
        if (quizResult.isPresent()) {
            return ResponseEntity.ok(quizResult.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/student/me")
    public ResponseEntity<List<QuizResult>> getMyQuizResults(HttpServletRequest request) {
        try {
            Long studentId = getCurrentUserId(request);
            List<QuizResult> quizResults = quizResultService.getQuizResultsByStudent(studentId);
            return ResponseEntity.ok(quizResults);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<QuizResult>> getQuizResultsByLesson(@PathVariable Long lessonId) {
        List<QuizResult> quizResults = quizResultService.getQuizResultsByLesson(lessonId);
        return ResponseEntity.ok(quizResults);
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QuizResult>> getQuizResultsByCourse(@PathVariable Long courseId) {
        List<QuizResult> quizResults = quizResultService.getQuizResultsByCourse(courseId);
        return ResponseEntity.ok(quizResults);
    }
    
    @GetMapping("/student/me/average")
    public ResponseEntity<?> getMyAverageMarks(HttpServletRequest request) {
        try {
            Long studentId = getCurrentUserId(request);
            Double average = quizResultService.getAverageMarksByStudent(studentId);
            return ResponseEntity.ok(Map.of("averageMarks", average != null ? average : 0.0));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuizResult(@PathVariable String id, @Valid @RequestBody QuizResultRequest request) {
        try {
            QuizResult quizResult = quizResultService.updateQuizResult(id, request);
            return ResponseEntity.ok(Map.of(
                "message", "Quiz result updated successfully",
                "quizResult", quizResult
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuizResult(@PathVariable String id) {
        try {
            quizResultService.deleteQuizResult(id);
            return ResponseEntity.ok(Map.of("message", "Quiz result deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
}
