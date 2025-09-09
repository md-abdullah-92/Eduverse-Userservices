package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    @Query("SELECT a FROM Assignment a WHERE a.teacher_id = :teacherId")
    List<Assignment> findByTeacherId(@Param("teacherId") Long teacherId);
    
    List<Assignment> findByTitleContaining(String title);
    
    @Query("SELECT a FROM Assignment a WHERE a.teacher_id = :teacherId ORDER BY a.created_at DESC")
    List<Assignment> findByTeacherIdOrderByCreatedAtDesc(@Param("teacherId") Long teacherId);
    
    @Query("SELECT a FROM Assignment a WHERE a.title LIKE %:title% OR a.description LIKE %:description%")
    List<Assignment> findByTitleContainingOrDescriptionContaining(@Param("title") String title, @Param("description") String description);
}
