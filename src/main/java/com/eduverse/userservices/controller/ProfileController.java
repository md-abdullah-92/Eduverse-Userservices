package com.eduverse.userservices.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduverse.userservices.dto.QuizResultResponse;
import com.eduverse.userservices.dto.StudentProfileRequest;
import com.eduverse.userservices.dto.StudentProfileResponse;
import com.eduverse.userservices.dto.TeacherProfileRequest;
import com.eduverse.userservices.dto.TeacherProfileResponse;
import com.eduverse.userservices.model.StudentProfile;
import com.eduverse.userservices.model.TeacherProfile;
import com.eduverse.userservices.model.User;
import com.eduverse.userservices.service.JwtService;
import com.eduverse.userservices.service.ProfileService;
import com.eduverse.userservices.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserService userService;
    
    private Long getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtService.extractUserId(token);
        }
        throw new RuntimeException("No valid token found");
    }

    // Map entity -> DTO with camelCase + nested user
    private StudentProfileResponse mapStudentProfile(StudentProfile sp) {
        StudentProfileResponse dto = new StudentProfileResponse();

        StudentProfileResponse.UserSummary user = new StudentProfileResponse.UserSummary();
        if (sp.getUser() != null) {
            user.setName(sp.getUser().getName());
            user.setRole(sp.getUser().getRole() != null ? sp.getUser().getRole().name() : null);
            user.setEmail(sp.getUser().getEmail());
            user.setPhone(sp.getUser().getPhone_number());
        }
        // Put bio both at root and under user as your UI expects user.bio
        user.setBio(sp.getBio());
        dto.setUser(user);

        dto.setProfilePhoto(sp.getProfile_photo());
        dto.setCoverPhoto(sp.getCover_photo());
        dto.setUserId(sp.getUser() != null && sp.getUser().getId() != null ? String.valueOf(sp.getUser().getId()) : null);

        dto.setEducationLevel(sp.getEducation_level());
        dto.setInstitution(sp.getInstitution());
        dto.setGuardianName(sp.getGuardian_name());
        dto.setGuardianPhone(sp.getGuardian_phone());
        dto.setGuardianEmail(sp.getGuardian_email());
        dto.setDateOfBirth(sp.getDate_of_birth());
        dto.setAddress(sp.getAddress());
        dto.setBio(sp.getBio());
        dto.setCreatedAt(sp.getCreated_at());
        
        // Add quiz results - with null checks
        if (sp.getUser() != null && sp.getUser().getId() != null) {
            List<QuizResultResponse> quizResults = profileService.getQuizResultsForStudent(sp.getUser().getId());
            dto.setQuizResults(quizResults);
        } else {
            dto.setQuizResults(new java.util.ArrayList<>());
        }

        return dto;
    }

    // Map teacher entity -> DTO
    private TeacherProfileResponse mapTeacherProfile(TeacherProfile tp) {
        TeacherProfileResponse dto = new TeacherProfileResponse();

        TeacherProfileResponse.UserSummary user = new TeacherProfileResponse.UserSummary();
        if (tp.getUser() != null) {
            user.setName(tp.getUser().getName());
            user.setRole(tp.getUser().getRole() != null ? tp.getUser().getRole().name() : null);
            user.setEmail(tp.getUser().getEmail());
            user.setPhone(tp.getUser().getPhone_number());
        }
        user.setBio(tp.getBio());
        dto.setUser(user);

        dto.setProfilePhoto(tp.getProfile_photo());
        dto.setCoverPhoto(tp.getCover_photo());
        dto.setUserId(tp.getUser() != null && tp.getUser().getId() != null ? String.valueOf(tp.getUser().getId()) : null);

        dto.setEducation(tp.getEducation());
        dto.setSpecialization(tp.getSpecialization());
        dto.setExperience(tp.getExperience());
        dto.setInstitution(tp.getInstitution());
        dto.setCertifications(tp.getCertifications());
        dto.setRating(tp.getRating());
        dto.setBio(tp.getBio());
        dto.setCreatedAt(tp.getCreated_at());

        // Add teacher content collections to match React hook expectations
        if (tp.getUser() != null && tp.getUser().getId() != null) {
            dto.setQuizzes(profileService.getQuizzesForTeacher(tp.getUser().getId()));
            dto.setAssignments(profileService.getAssignmentsForTeacher(tp.getUser().getId()));
            dto.setStudyNotes(profileService.getStudyNotesForTeacher(tp.getUser().getId()));
        } else {
            dto.setQuizzes(new java.util.ArrayList<>());
            dto.setAssignments(new java.util.ArrayList<>());
            dto.setStudyNotes(new java.util.ArrayList<>());
        }

        return dto;
    }
    
    // GET /api/profile - current user's profile (Node-like unified shape)
    @GetMapping("")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            // Try student first
            Optional<StudentProfile> studentProfile = profileService.getStudentProfile(userId);
            if (studentProfile.isPresent()) {
                StudentProfileResponse dto = mapStudentProfile(studentProfile.get());
                return ResponseEntity.ok(Map.of(
                    "studentProfile", dto
                ));
            }
            // Then teacher
            Optional<TeacherProfile> teacherProfile = profileService.getTeacherProfile(userId);
            if (teacherProfile.isPresent()) {
                TeacherProfileResponse dto = mapTeacherProfile(teacherProfile.get());
                return ResponseEntity.ok(Map.of(
                    "teacherProfile", dto
                ));
            }
            return ResponseEntity.ok(Map.of(
                "studentProfile", null,
                "teacherProfile", null
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    
    // Generic Profile Endpoint - returns { studentProfile } or { teacherProfile } (public, Node-like)
    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfileById(@PathVariable Long userId) {
        try {
            // Try to get student profile first
            Optional<StudentProfile> studentProfile = profileService.getStudentProfile(userId);
            if (studentProfile.isPresent()) {
                StudentProfileResponse dto = mapStudentProfile(studentProfile.get());
                return ResponseEntity.ok(Map.of(
                    "studentProfile", dto
                ));
            }
            // Try to get teacher profile
            Optional<TeacherProfile> teacherProfile = profileService.getTeacherProfile(userId);
            if (teacherProfile.isPresent()) {
                TeacherProfileResponse dto = mapTeacherProfile(teacherProfile.get());
                return ResponseEntity.ok(Map.of(
                    "teacherProfile", dto
                ));
            }
            // If neither profile exists, return appropriate error response
            return ResponseEntity.status(404).body(Map.of(
                "message", "Profile not found for user ID: " + userId
            ));
        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            return ResponseEntity.status(500).body(Map.of(
                "message", "Internal server error: " + e.getMessage()
            ));
        }
    }
    
    // Student Profile Endpoints (Node-compatible: PUT /api/profile/student)
    @PutMapping("/student")
    public ResponseEntity<?> createOrUpdateStudentProfile(
            @Valid @RequestBody StudentProfileRequest request,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getCurrentUserId(httpRequest);
            StudentProfile profile = profileService.createOrUpdateStudentProfile(userId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Student profile updated successfully",
                "profile", profile
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping("/student/me")
    public ResponseEntity<?> getMyStudentProfile(HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            Optional<StudentProfile> profile = profileService.getStudentProfile(userId);
            if (profile.isPresent()) {
                return ResponseEntity.ok(profile.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping({"/students", "/student/all"})
    public ResponseEntity<List<StudentProfile>> getAllStudentProfiles() {
        List<StudentProfile> profiles = profileService.getAllStudentProfiles();
        return ResponseEntity.ok(profiles);
    }
    
    // Teacher Profile Endpoints (Node-compatible: PUT /api/profile/teacher)
    @PutMapping("/teacher")
    public ResponseEntity<?> createOrUpdateTeacherProfile(
            @Valid @RequestBody TeacherProfileRequest request,
            HttpServletRequest httpRequest) {
        try {
            Long userId = getCurrentUserId(httpRequest);
            TeacherProfile profile = profileService.createOrUpdateTeacherProfile(userId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Teacher profile updated successfully",
                "profile", profile
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping("/teacher/me")
    public ResponseEntity<?> getMyTeacherProfile(HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            Optional<TeacherProfile> profile = profileService.getTeacherProfile(userId);
            if (profile.isPresent()) {
                return ResponseEntity.ok(profile.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping({"/teachers", "/teacher/all"})
    public ResponseEntity<List<TeacherProfile>> getAllTeacherProfiles() {
        List<TeacherProfile> profiles = profileService.getAllTeacherProfiles();
        return ResponseEntity.ok(profiles);
    }
    
    @GetMapping("/teachers/specialization/{specialization}")
    public ResponseEntity<List<TeacherProfile>> getTeachersBySpecialization(@PathVariable String specialization) {
        List<TeacherProfile> profiles = profileService.getTeachersBySpecialization(specialization);
        return ResponseEntity.ok(profiles);
    }
    
    @GetMapping("/teachers/top-rated")
    public ResponseEntity<List<TeacherProfile>> getTopRatedTeachers() {
        List<TeacherProfile> profiles = profileService.getTopRatedTeachers();
        return ResponseEntity.ok(profiles);
    }
    
    // Node-compatible: PUT /api/profile/user -> update basic user fields
    @PutMapping("/user")
    public ResponseEntity<?> updateUserProfile(@RequestBody User userDetails, HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            User updatedUser = userService.updateUser(userId, userDetails);
            return ResponseEntity.ok(Map.of(
                "message", "User profile updated successfully",
                "user", updatedUser
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
