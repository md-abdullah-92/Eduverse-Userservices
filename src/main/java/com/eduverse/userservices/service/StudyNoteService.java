package com.eduverse.userservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduverse.userservices.dto.StudyNoteRequest;
import com.eduverse.userservices.model.StudyNote;
import com.eduverse.userservices.repository.StudyNoteRepository;
import com.eduverse.userservices.repository.TeacherProfileRepository;

@Service
public class StudyNoteService {
    
    @Autowired
    private StudyNoteRepository studyNoteRepository;
    
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
    
    public StudyNote createStudyNote(Long teacherId, StudyNoteRequest request) {
        // Verify teacher exists
        teacherProfileRepository.findByUserId(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));
        
        StudyNote studyNote = new StudyNote();
        studyNote.setTitle(request.getTitle());
        studyNote.setDescription(request.getDescription());
        studyNote.setTeacher_id(teacherId);
        
        return studyNoteRepository.save(studyNote);
    }
    
    public List<StudyNote> getAllStudyNotes() {
        return studyNoteRepository.findAll();
    }
    
    public Optional<StudyNote> getStudyNoteById(Long id) {
        return studyNoteRepository.findById(id);
    }
    
    public List<StudyNote> getStudyNotesByTeacher(Long teacherId) {
        return studyNoteRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId);
    }
    
    public StudyNote updateStudyNote(Long id, StudyNoteRequest request) {
        StudyNote studyNote = studyNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study note not found"));
        
        studyNote.setTitle(request.getTitle());
        studyNote.setDescription(request.getDescription());
        
        return studyNoteRepository.save(studyNote);
    }
    
    public void deleteStudyNote(Long id) {
        StudyNote studyNote = studyNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study note not found"));
        studyNoteRepository.delete(studyNote);
    }
    
    public List<StudyNote> searchStudyNotes(String query) {
        return studyNoteRepository.findByTitleContainingOrDescriptionContaining(query, query);
    }
}
