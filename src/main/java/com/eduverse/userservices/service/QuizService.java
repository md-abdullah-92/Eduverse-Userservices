package com.eduverse.userservices.service;

import com.eduverse.userservices.dto.QuizRequest;
import com.eduverse.userservices.model.Quiz;
import com.eduverse.userservices.model.TeacherProfile;
import com.eduverse.userservices.repository.QuizRepository;
import com.eduverse.userservices.repository.TeacherProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    
    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
    
    public Quiz createQuiz(Long teacherId, QuizRequest request) {
        // Verify teacher exists
        teacherProfileRepository.findByUserId(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));
        
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setDuration(request.getDuration());
        quiz.setTeacher_id(teacherId);
        
        return quizRepository.save(quiz);
    }
    
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
    
    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }
    
    public List<Quiz> getQuizzesByTeacher(Long teacherId) {
        return quizRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId);
    }
    
    public Quiz updateQuiz(String id, QuizRequest request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setDuration(request.getDuration());
        
        return quizRepository.save(quiz);
    }
    
    public void deleteQuiz(String id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quizRepository.delete(quiz);
    }
    
    public List<Quiz> searchQuizzes(String title) {
        return quizRepository.findByTitleContaining(title);
    }
}
