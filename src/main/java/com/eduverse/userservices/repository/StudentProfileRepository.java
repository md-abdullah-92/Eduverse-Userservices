package com.eduverse.userservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduverse.userservices.model.StudentProfile;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    
    Optional<StudentProfile> findByUserId(Long userId);
    
    @Query("SELECT sp FROM StudentProfile sp JOIN FETCH sp.user WHERE sp.user.id = :userId")
    Optional<StudentProfile> findByUserIdWithUser(@Param("userId") Long userId);
    
    @Query("SELECT sp FROM StudentProfile sp WHERE sp.education_level = :educationLevel")
    List<StudentProfile> findByEducationLevel(@Param("educationLevel") String educationLevel);
    
    List<StudentProfile> findByInstitution(String institution);
    
    @Query("SELECT sp FROM StudentProfile sp WHERE sp.guardian_name LIKE %:guardianName%")
    List<StudentProfile> findByGuardianNameContaining(@Param("guardianName") String guardianName);
    
    @Query("SELECT sp FROM StudentProfile sp WHERE sp.institution LIKE %:institution% AND sp.education_level = :educationLevel")
    List<StudentProfile> findByInstitutionContainingAndEducationLevel(@Param("institution") String institution, @Param("educationLevel") String educationLevel);
    
    boolean existsByUserId(Long userId);
}
