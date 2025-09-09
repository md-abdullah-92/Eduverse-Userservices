package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.StudyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyNoteRepository extends JpaRepository<StudyNote, Long> {
    
    @Query("SELECT sn FROM StudyNote sn WHERE sn.teacher_id = :teacherId")
    List<StudyNote> findByTeacherId(@Param("teacherId") Long teacherId);
    
    List<StudyNote> findByTitleContaining(String title);
    
    @Query("SELECT sn FROM StudyNote sn WHERE sn.teacher_id = :teacherId ORDER BY sn.created_at DESC")
    List<StudyNote> findByTeacherIdOrderByCreatedAtDesc(@Param("teacherId") Long teacherId);
    
    @Query("SELECT sn FROM StudyNote sn WHERE sn.title LIKE %:title% OR sn.description LIKE %:description%")
    List<StudyNote> findByTitleContainingOrDescriptionContaining(@Param("title") String title, @Param("description") String description);
}
