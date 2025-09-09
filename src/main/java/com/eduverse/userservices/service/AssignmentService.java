package com.eduverse.userservices.service;

import com.eduverse.userservices.dto.AssignmentRequest;
import com.eduverse.userservices.model.Assignment;
import com.eduverse.userservices.model.TeacherProfile;
import com.eduverse.userservices.repository.AssignmentRepository;
import com.eduverse.userservices.repository.TeacherProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
    
    public Assignment createAssignment(Long teacherId, AssignmentRequest request) {
        // Verify teacher exists
        teacherProfileRepository.findByUserId(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));
        
        Assignment assignment = new Assignment();
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setTeacher_id(teacherId);
        
        return assignmentRepository.save(assignment);
    }
    
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    
    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }
    
    public List<Assignment> getAssignmentsByTeacher(Long teacherId) {
        return assignmentRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId);
    }
    
    public Assignment updateAssignment(Long id, AssignmentRequest request) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        
        return assignmentRepository.save(assignment);
    }
    
    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignmentRepository.delete(assignment);
    }
    
    public List<Assignment> searchAssignments(String query) {
        return assignmentRepository.findByTitleContainingOrDescriptionContaining(query, query);
    }
}
