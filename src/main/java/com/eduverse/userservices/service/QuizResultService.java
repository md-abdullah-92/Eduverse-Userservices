package com.eduverse.userservices.service;

import com.eduverse.userservices.dto.QuizResultRequest;
import com.eduverse.userservices.model.QuizResult;
import com.eduverse.userservices.model.StudentProfile;
import com.eduverse.userservices.repository.QuizResultRepository;
import com.eduverse.userservices.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizResultService {
    
    @Autowired
    private QuizResultRepository quizResultRepository;
    
    @Autowired
    private StudentProfileRepository studentProfileRepository;
    
    public QuizResult createQuizResult(Long studentId, QuizResultRequest request) {
        // Verify student exists
        if (!studentProfileRepository.existsById(studentId)) {
            throw new RuntimeException("Student profile not found");
        }
        
        QuizResult quizResult = new QuizResult();
        quizResult.setTitle(request.getTitle());
        quizResult.setMarks(request.getMarks());
        quizResult.setFull_mark(request.getFullMark());
        quizResult.setStudent_id(studentId);
        quizResult.setLesson_id(request.getLessonId());
        quizResult.setCourse_id(request.getCourseId());
        
        return quizResultRepository.save(quizResult);
    }
    
    public List<QuizResult> getAllQuizResults() {
        return quizResultRepository.findAll();
    }
    
    public Optional<QuizResult> getQuizResultById(String id) {
        return quizResultRepository.findById(id);
    }
    
    public List<QuizResult> getQuizResultsByStudent(Long studentId) {
        return quizResultRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
    }
    
    public List<QuizResult> getQuizResultsByLesson(Long lessonId) {
        return quizResultRepository.findByLessonId(lessonId);
    }
    
    public List<QuizResult> getQuizResultsByCourse(Long courseId) {
        return quizResultRepository.findByCourseId(courseId);
    }
    
    public Double getAverageMarksByStudent(Long studentId) {
        return quizResultRepository.findAverageMarksByStudentId(studentId);
    }
    
    public QuizResult updateQuizResult(String id, QuizResultRequest request) {
        QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz result not found"));
        
        quizResult.setTitle(request.getTitle());
        quizResult.setMarks(request.getMarks());
        quizResult.setFull_mark(request.getFullMark());
        quizResult.setLesson_id(request.getLessonId());
        quizResult.setCourse_id(request.getCourseId());
        
        return quizResultRepository.save(quizResult);
    }
    
    public void deleteQuizResult(String id) {
        QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz result not found"));
        quizResultRepository.delete(quizResult);
    }
}
