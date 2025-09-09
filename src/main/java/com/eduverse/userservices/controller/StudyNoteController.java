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

import com.eduverse.userservices.dto.StudyNoteRequest;
import com.eduverse.userservices.model.StudyNote;
import com.eduverse.userservices.service.JwtService;
import com.eduverse.userservices.service.StudyNoteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/studynote")
@CrossOrigin(origins = "*")
public class StudyNoteController {
    
    @Autowired
    private StudyNoteService studyNoteService;
    
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
    public ResponseEntity<?> createStudyNote(@Valid @RequestBody StudyNoteRequest request, HttpServletRequest httpRequest) {
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
            
            StudyNote studyNote = studyNoteService.createStudyNote(teacherId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Study note created successfully",
                "studyNote", studyNote
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<StudyNote>> getAllStudyNotes() {
        List<StudyNote> studyNotes = studyNoteService.getAllStudyNotes();
        return ResponseEntity.ok(studyNotes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudyNoteById(@PathVariable Long id) {
        Optional<StudyNote> studyNote = studyNoteService.getStudyNoteById(id);
        if (studyNote.isPresent()) {
            return ResponseEntity.ok(studyNote.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<StudyNote>> getStudyNotesByTeacher(@PathVariable Long teacherId) {
        try {
            List<StudyNote> studyNotes = studyNoteService.getStudyNotesByTeacher(teacherId);
            return ResponseEntity.ok(studyNotes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/teacher/me")
    public ResponseEntity<List<StudyNote>> getMyStudyNotes(HttpServletRequest request) {
        try {
            Long teacherId = getCurrentUserId(request);
            if (teacherId == null) {
                return ResponseEntity.badRequest().build();
            }
            List<StudyNote> studyNotes = studyNoteService.getStudyNotesByTeacher(teacherId);
            return ResponseEntity.ok(studyNotes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudyNote(@PathVariable Long id, @Valid @RequestBody StudyNoteRequest request) {
        try {
            StudyNote studyNote = studyNoteService.updateStudyNote(id, request);
            return ResponseEntity.ok(Map.of(
                "message", "Study note updated successfully",
                "studyNote", studyNote
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudyNoteAlt(@PathVariable Long id) {
        try {
            studyNoteService.deleteStudyNote(id);
            return ResponseEntity.ok(Map.of("message", "Study note deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudyNote(@PathVariable Long id) {
        try {
            studyNoteService.deleteStudyNote(id);
            return ResponseEntity.ok(Map.of("message", "Study note deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<StudyNote>> searchStudyNotes(@RequestParam String query) {
        List<StudyNote> studyNotes = studyNoteService.searchStudyNotes(query);
        return ResponseEntity.ok(studyNotes);
    }
}
