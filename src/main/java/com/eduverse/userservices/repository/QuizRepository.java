package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    
    @Query("SELECT q FROM Quiz q WHERE q.teacher_id = :teacherId")
    List<Quiz> findByTeacherId(@Param("teacherId") Long teacherId);
    
    List<Quiz> findByTitleContaining(String title);
    
    @Query("SELECT q FROM Quiz q WHERE q.teacher_id = :teacherId ORDER BY q.created_at DESC")
    List<Quiz> findByTeacherIdOrderByCreatedAtDesc(@Param("teacherId") Long teacherId);
    
    @Query("SELECT q FROM Quiz q WHERE q.duration BETWEEN :minDuration AND :maxDuration")
    List<Quiz> findByDurationBetween(@Param("minDuration") Integer minDuration, @Param("maxDuration") Integer maxDuration);
}
