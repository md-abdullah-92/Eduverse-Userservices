package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.User;
import com.eduverse.userservices.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.is_verified = :isVerified")
    Optional<User> findByEmailAndIsVerified(@Param("email") String email, @Param("isVerified") Boolean isVerified);
    
    Optional<User> findByOtp(String otp);
    
    @Query("SELECT u FROM User u WHERE u.otp = :otp AND u.otp_expires_at > :currentTime")
    Optional<User> findByOtpAndOtpExpiresAtAfter(@Param("otp") String otp, @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.otp = :otp AND u.otp_expires_at > :currentTime")
    Optional<User> findByEmailAndOtpAndOtpExpiresAtAfter(@Param("email") String email, @Param("otp") String otp, @Param("currentTime") LocalDateTime currentTime);
    
    List<User> findByRole(Role role);
    
    @Query("SELECT u FROM User u WHERE u.is_verified = :isVerified")
    List<User> findByIsVerified(@Param("isVerified") Boolean isVerified);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.otp IS NOT NULL AND u.otp_expires_at < :currentTime")
    List<User> findUsersWithExpiredOtp(@Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% OR u.email LIKE %:email%")
    List<User> findByNameContainingOrEmailContaining(@Param("name") String name, @Param("email") String email);
}
