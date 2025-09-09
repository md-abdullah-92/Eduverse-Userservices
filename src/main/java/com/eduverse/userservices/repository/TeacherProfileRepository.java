package com.eduverse.userservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduverse.userservices.model.TeacherProfile;

@Repository
public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {
    
    Optional<TeacherProfile> findByUserId(Long userId);
    
    @Query("SELECT tp FROM TeacherProfile tp JOIN FETCH tp.user WHERE tp.user.id = :userId")
    Optional<TeacherProfile> findByUserIdWithUser(@Param("userId") Long userId);
    
    List<TeacherProfile> findBySpecialization(String specialization);
    
    List<TeacherProfile> findByInstitution(String institution);
    
    List<TeacherProfile> findByExperienceGreaterThanEqual(Integer experience);
    
    List<TeacherProfile> findByRatingGreaterThanEqual(Double rating);
    
    @Query("SELECT tp FROM TeacherProfile tp WHERE tp.specialization LIKE %:specialization% AND tp.experience >= :minExperience")
    List<TeacherProfile> findBySpecializationContainingAndExperienceGreaterThanEqual(
        @Param("specialization") String specialization, 
        @Param("minExperience") Integer minExperience
    );
    
    @Query("SELECT tp FROM TeacherProfile tp ORDER BY tp.rating DESC")
    List<TeacherProfile> findAllOrderByRatingDesc();
    
    boolean existsByUserId(Long userId);
}
