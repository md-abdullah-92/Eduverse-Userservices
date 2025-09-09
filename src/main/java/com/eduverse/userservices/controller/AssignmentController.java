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

import com.eduverse.userservices.dto.AssignmentRequest;
import com.eduverse.userservices.model.Assignment;
import com.eduverse.userservices.service.AssignmentService;
import com.eduverse.userservices.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assignment")
@CrossOrigin(origins = "*")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
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
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssignmentRequest request, HttpServletRequest httpRequest) {
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
            
            Assignment assignment = assignmentService.createAssignment(teacherId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment created successfully",
                "assignment", assignment
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.getAssignmentById(id);
        if (assignment.isPresent()) {
            return ResponseEntity.ok(assignment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByTeacher(@PathVariable Long teacherId) {
        try {
            List<Assignment> assignments = assignmentService.getAssignmentsByTeacher(teacherId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/teacher/me")
    public ResponseEntity<List<Assignment>> getMyAssignments(HttpServletRequest request) {
        try {
            Long teacherId = getCurrentUserId(request);
            if (teacherId == null) {
                return ResponseEntity.badRequest().build();
            }
            List<Assignment> assignments = assignmentService.getAssignmentsByTeacher(teacherId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long id, @Valid @RequestBody AssignmentRequest request) {
        try {
            Assignment assignment = assignmentService.updateAssignment(id, request);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment updated successfully",
                "assignment", assignment
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAssignmentAlt(@PathVariable Long id) {
        try {
            assignmentService.deleteAssignment(id);
            return ResponseEntity.ok(Map.of("message", "Assignment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        try {
            assignmentService.deleteAssignment(id);
            return ResponseEntity.ok(Map.of("message", "Assignment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Assignment>> searchAssignments(@RequestParam String query) {
        List<Assignment> assignments = assignmentService.searchAssignments(query);
        return ResponseEntity.ok(assignments);
    }
}
