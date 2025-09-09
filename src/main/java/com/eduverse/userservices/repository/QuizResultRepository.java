package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, String> {
    
    @Query("SELECT qr FROM QuizResult qr WHERE qr.student_id = :studentId")
    List<QuizResult> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT qr FROM QuizResult qr WHERE qr.lesson_id = :lessonId")
    List<QuizResult> findByLessonId(@Param("lessonId") Long lessonId);
    
    @Query("SELECT qr FROM QuizResult qr WHERE qr.course_id = :courseId")
    List<QuizResult> findByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT qr FROM QuizResult qr WHERE qr.student_id = :studentId ORDER BY qr.created_at DESC")
    List<QuizResult> findByStudentIdOrderByCreatedAtDesc(@Param("studentId") Long studentId);
    
    @Query("SELECT qr FROM QuizResult qr WHERE qr.student_id = :studentId AND qr.marks >= :minMarks")
    List<QuizResult> findByStudentIdAndMarksGreaterThanEqual(@Param("studentId") Long studentId, @Param("minMarks") Integer minMarks);
    
    @Query("SELECT AVG(qr.marks) FROM QuizResult qr WHERE qr.student_id = :studentId")
    Double findAverageMarksByStudentId(@Param("studentId") Long studentId);
}
