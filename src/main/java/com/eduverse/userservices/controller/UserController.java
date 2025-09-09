package com.eduverse.userservices.controller;

import com.eduverse.userservices.model.User;
import com.eduverse.userservices.service.JwtService;
import com.eduverse.userservices.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtService jwtService;
    
    private Long getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtService.extractUserId(token);
        }
        throw new RuntimeException("No valid token found");
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            Optional<User> user = userService.getUserById(userId);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestBody User userDetails, HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            User updatedUser = userService.updateUser(userId, userDetails);
            return ResponseEntity.ok(Map.of(
                "message", "User updated successfully",
                "user", updatedUser
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteCurrentUser(HttpServletRequest request) {
        try {
            Long userId = getCurrentUserId(request);
            userService.deleteUser(userId);
            return ResponseEntity.ok(Map.of("message", "User account deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(Map.of(
                "message", "User updated successfully",
                "user", updatedUser
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
}
