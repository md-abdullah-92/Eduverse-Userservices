package com.eduverse.userservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduverse.userservices.dto.AnsweredQuestionResponse;
import com.eduverse.userservices.dto.QuizResultResponse;
import com.eduverse.userservices.dto.StudentProfileRequest;
import com.eduverse.userservices.dto.TeacherProfileResponse;
import com.eduverse.userservices.dto.TeacherProfileRequest;
import com.eduverse.userservices.model.AnsweredQuestion;
import com.eduverse.userservices.model.Assignment;
import com.eduverse.userservices.model.Question;
import com.eduverse.userservices.model.Quiz;
import com.eduverse.userservices.model.QuizResult;
import com.eduverse.userservices.model.StudentProfile;
import com.eduverse.userservices.model.TeacherProfile;
import com.eduverse.userservices.model.User;
import com.eduverse.userservices.model.enums.Role;
import com.eduverse.userservices.model.enums.DifficultyLevel;
import com.eduverse.userservices.model.enums.QuestionType;
import com.eduverse.userservices.repository.AnsweredQuestionRepository;
import com.eduverse.userservices.repository.AssignmentRepository;
import com.eduverse.userservices.repository.QuestionRepository;
import com.eduverse.userservices.repository.QuizRepository;
import com.eduverse.userservices.repository.QuizResultRepository;
import com.eduverse.userservices.repository.StudentProfileRepository;
import com.eduverse.userservices.repository.StudyNoteRepository;
import com.eduverse.userservices.repository.TeacherProfileRepository;
import com.eduverse.userservices.repository.UserRepository;

@Service
public class ProfileService {
    
    @Autowired
    private StudentProfileRepository studentProfileRepository;
    
    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private QuizResultRepository quizResultRepository;
    
    @Autowired
    private AnsweredQuestionRepository answeredQuestionRepository;

    // Repositories for teacher content
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudyNoteRepository studyNoteRepository;
    
    // Student Profile Methods
    public StudentProfile createOrUpdateStudentProfile(Long userId, StudentProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can have student profiles");
        }
        
        Optional<StudentProfile> existingProfile = studentProfileRepository.findByUserId(userId);
        
        StudentProfile profile;
        if (existingProfile.isPresent()) {
            profile = existingProfile.get();
            updateStudentProfileFields(profile, request);
        } else {
            profile = new StudentProfile();
            profile.setUser(user);
            updateStudentProfileFields(profile, request);
        }
        
        return studentProfileRepository.save(profile);
    }
    
    private void updateStudentProfileFields(StudentProfile profile, StudentProfileRequest request) {
        profile.setEducation_level(request.getEducationLevel());
        profile.setInstitution(request.getInstitution());
        profile.setGuardian_name(request.getGuardianName());
        profile.setGuardian_phone(request.getGuardianPhone());
        profile.setGuardian_email(request.getGuardianEmail());
        profile.setDate_of_birth(request.getDateOfBirth());
        profile.setAddress(request.getAddress());
        profile.setCover_photo(request.getCoverPhoto());
        profile.setProfile_photo(request.getProfilePhoto());
        profile.setBio(request.getBio());
    }
    
    public Optional<StudentProfile> getStudentProfile(Long userId) {
        return studentProfileRepository.findByUserIdWithUser(userId);
    }
    
    public List<StudentProfile> getAllStudentProfiles() {
        return studentProfileRepository.findAll();
    }
    
    // Teacher Profile Methods
    public TeacherProfile createOrUpdateTeacherProfile(Long userId, TeacherProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getRole() != Role.TEACHER) {
            throw new RuntimeException("Only teachers can have teacher profiles");
        }
        
        Optional<TeacherProfile> existingProfile = teacherProfileRepository.findByUserId(userId);
        
        TeacherProfile profile;
        if (existingProfile.isPresent()) {
            profile = existingProfile.get();
            updateTeacherProfileFields(profile, request);
        } else {
            profile = new TeacherProfile();
            profile.setUser(user);
            updateTeacherProfileFields(profile, request);
        }
        
        return teacherProfileRepository.save(profile);
    }
    
    private void updateTeacherProfileFields(TeacherProfile profile, TeacherProfileRequest request) {
        profile.setEducation(request.getEducation());
        profile.setSpecialization(request.getSpecialization());
        profile.setExperience(request.getExperience());
        profile.setInstitution(request.getInstitution());
        profile.setCertifications(request.getCertifications());
        profile.setRating(request.getRating());
        profile.setCover_photo(request.getCoverPhoto());
        profile.setProfile_photo(request.getProfilePhoto());
        profile.setBio(request.getBio());
    }
    
    public Optional<TeacherProfile> getTeacherProfile(Long userId) {
        return teacherProfileRepository.findByUserIdWithUser(userId);
    }
    
    public List<TeacherProfile> getAllTeacherProfiles() {
        return teacherProfileRepository.findAll();
    }
    
    public List<TeacherProfile> getTeachersBySpecialization(String specialization) {
        return teacherProfileRepository.findBySpecialization(specialization);
    }
    
    public List<TeacherProfile> getTopRatedTeachers() {
        return teacherProfileRepository.findAllOrderByRatingDesc();
    }
    
    // Helper method to map AnsweredQuestion to AnsweredQuestionResponse
    public AnsweredQuestionResponse mapAnsweredQuestion(AnsweredQuestion aq) {
        try {
            AnsweredQuestionResponse dto = new AnsweredQuestionResponse();
            dto.setId(aq.getId());
            dto.setQuestion(aq.getQuestion());
            dto.setCorrectAnswer(aq.getCorrect_answer());
            dto.setUserAnswer(aq.getUser_answer());
            dto.setExplanation(aq.getExplanation());
            dto.setDifficulty(aq.getDifficulty() != null ? aq.getDifficulty().name() : null);
            dto.setType(aq.getType() != null ? aq.getType().name() : null);
            dto.setQuizId(aq.getQuiz_id());
            dto.setQuizResultId(aq.getQuiz_result_id());
            
            // Handle options - convert from Map to List<String>
            if (aq.getOptions() != null) {
                try {
                    java.util.List<String> optionsList = new java.util.ArrayList<>();
                    aq.getOptions().forEach((key, value) -> {
                        if (value != null) {
                            optionsList.add(value.toString());
                        }
                    });
                    dto.setOptions(optionsList);
                } catch (Exception e) {
                    System.err.println("Error processing options for answered question " + aq.getId() + ": " + e.getMessage());
                    dto.setOptions(new java.util.ArrayList<>());
                }
            }
            
            return dto;
        } catch (Exception e) {
            System.err.println("Error mapping answered question " + (aq != null ? aq.getId() : "null") + ": " + e.getMessage());
            // Return a minimal DTO
            AnsweredQuestionResponse dto = new AnsweredQuestionResponse();
            dto.setId(aq != null ? aq.getId() : "unknown");
            dto.setQuestion(aq != null ? aq.getQuestion() : "Unknown");
            dto.setOptions(new java.util.ArrayList<>());
            return dto;
        }
    }
    
    // Helper method to map QuizResult to QuizResultResponse
    public QuizResultResponse mapQuizResult(QuizResult qr) {
        try {
            QuizResultResponse dto = new QuizResultResponse();
            dto.setId(qr.getId());
            dto.setLessonId(qr.getLesson_id() != null ? qr.getLesson_id().toString() : null);
            dto.setCourseId(qr.getCourse_id() != null ? qr.getCourse_id().toString() : null);
            dto.setTitle(qr.getTitle());
            dto.setStudentId(qr.getStudent_id() != null ? qr.getStudent_id().toString() : null);
            dto.setFullmark(qr.getFull_mark());
            dto.setMarks(qr.getMarks());
            dto.setCreatedAt(qr.getCreated_at() != null ? qr.getCreated_at().toString() : null);
            
            // Get answered questions for this quiz result
            try {
                java.util.List<AnsweredQuestion> answeredQuestions = answeredQuestionRepository.findByQuizResultId(qr.getId());
                java.util.List<AnsweredQuestionResponse> answeredQuestionDTOs = new java.util.ArrayList<>();
                for (AnsweredQuestion aq : answeredQuestions) {
                    try {
                        answeredQuestionDTOs.add(mapAnsweredQuestion(aq));
                    } catch (Exception e) {
                        System.err.println("Error mapping answered question " + aq.getId() + ": " + e.getMessage());
                    }
                }
                dto.setAnsweredQuestions(answeredQuestionDTOs);
            } catch (Exception e) {
                System.err.println("Error fetching answered questions for quiz result " + qr.getId() + ": " + e.getMessage());
                dto.setAnsweredQuestions(new java.util.ArrayList<>());
            }
            
            return dto;
        } catch (Exception e) {
            System.err.println("Error mapping quiz result " + (qr != null ? qr.getId() : "null") + ": " + e.getMessage());
            // Return a minimal DTO
            QuizResultResponse dto = new QuizResultResponse();
            dto.setId(qr != null ? qr.getId() : "unknown");
            dto.setTitle(qr != null ? qr.getTitle() : "Unknown");
            dto.setAnsweredQuestions(new java.util.ArrayList<>());
            return dto;
        }
    }
    
    // Method to get quiz results for a student
    public java.util.List<QuizResultResponse> getQuizResultsForStudent(Long studentId) {
        try {
            if (studentId == null) {
                return new java.util.ArrayList<>();
            }
            java.util.List<QuizResult> quizResults = quizResultRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
            java.util.List<QuizResultResponse> quizResultDTOs = new java.util.ArrayList<>();
            for (QuizResult qr : quizResults) {
                quizResultDTOs.add(mapQuizResult(qr));
            }
            return quizResultDTOs;
        } catch (Exception e) {
            // Log the error and return empty list
            System.err.println("Error fetching quiz results for student " + studentId + ": " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    // ========================= Teacher content mapping =========================
    private String toLower(Enum<?> e) {
        return e != null ? e.name().toLowerCase() : null;
    }

    private List<String> mapOptions(java.util.Map<String, Object> options) {
        if (options == null) return new java.util.ArrayList<>();
        java.util.List<String> list = new java.util.ArrayList<>();
        options.forEach((k, v) -> { if (v != null) list.add(String.valueOf(v)); });
        return list;
    }

    private TeacherProfileResponse.QuizSummary mapQuiz(Quiz q) {
        TeacherProfileResponse.QuizSummary qs = new TeacherProfileResponse.QuizSummary();
        qs.setId(q.getId());
        qs.setTitle(q.getTitle());
        qs.setDescription(q.getDescription());
        qs.setDuration(q.getDuration());
        qs.setCreatedAt(q.getCreated_at());

        // Questions
        java.util.List<Question> questions = questionRepository.findByQuizId(q.getId());
        java.util.List<TeacherProfileResponse.QuizQuestion> questionDTOs = new java.util.ArrayList<>();
        for (Question qu : questions) {
            TeacherProfileResponse.QuizQuestion qq = new TeacherProfileResponse.QuizQuestion();
            qq.setQuestion(qu.getQuestion());
            qq.setOptions(mapOptions(qu.getOptions()));
            qq.setCorrectAnswer(qu.getCorrect_answer());
            qq.setExplanation(qu.getExplanation());
            // Convert enums to expected lowercase strings
            QuestionType type = qu.getType();
            DifficultyLevel diff = qu.getDifficulty();
            qq.setType(toLower(type));
            qq.setDifficulty(toLower(diff));
            questionDTOs.add(qq);
        }
        qs.setQuestions(questionDTOs);
        return qs;
    }

    public java.util.List<TeacherProfileResponse.QuizSummary> getQuizzesForTeacher(Long teacherUserId) {
        if (teacherUserId == null) return new java.util.ArrayList<>();
        java.util.List<Quiz> quizzes = quizRepository.findByTeacherIdOrderByCreatedAtDesc(teacherUserId);
        java.util.List<TeacherProfileResponse.QuizSummary> list = new java.util.ArrayList<>();
        for (Quiz q : quizzes) {
            list.add(mapQuiz(q));
        }
        return list;
    }

    public java.util.List<TeacherProfileResponse.AssignmentSummary> getAssignmentsForTeacher(Long teacherUserId) {
        if (teacherUserId == null) return new java.util.ArrayList<>();
        java.util.List<Assignment> items = assignmentRepository.findByTeacherIdOrderByCreatedAtDesc(teacherUserId);
        java.util.List<TeacherProfileResponse.AssignmentSummary> list = new java.util.ArrayList<>();
        for (Assignment a : items) {
            TeacherProfileResponse.AssignmentSummary as = new TeacherProfileResponse.AssignmentSummary();
            as.setId(a.getId());
            as.setTitle(a.getTitle());
            as.setDescription(a.getDescription());
            as.setCreatedAt(a.getCreated_at());
            list.add(as);
        }
        return list;
    }

    public java.util.List<TeacherProfileResponse.StudyNoteSummary> getStudyNotesForTeacher(Long teacherUserId) {
        if (teacherUserId == null) return new java.util.ArrayList<>();
        java.util.List<com.eduverse.userservices.model.StudyNote> items = studyNoteRepository.findByTeacherIdOrderByCreatedAtDesc(teacherUserId);
        java.util.List<TeacherProfileResponse.StudyNoteSummary> list = new java.util.ArrayList<>();
        for (com.eduverse.userservices.model.StudyNote sn : items) {
            TeacherProfileResponse.StudyNoteSummary ss = new TeacherProfileResponse.StudyNoteSummary();
            ss.setId(sn.getId());
            ss.setTitle(sn.getTitle());
            ss.setDescription(sn.getDescription());
            ss.setCreatedAt(sn.getCreated_at());
            list.add(ss);
        }
        return list;
    }
}
