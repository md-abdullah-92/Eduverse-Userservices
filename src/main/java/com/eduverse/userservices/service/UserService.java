package com.eduverse.userservices.service;

import com.eduverse.userservices.model.User;
import com.eduverse.userservices.model.enums.Role;
import com.eduverse.userservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
        builder.password(user.getPassword());
        builder.roles(user.getRole().toString());
        
        return builder.build();
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setName(userDetails.getName());
        user.setPhone_number(userDetails.getPhone_number());
        user.setProfile_image(userDetails.getProfile_image());
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
    
    public List<User> searchUsers(String query) {
        return userRepository.findByNameContainingOrEmailContaining(query, query);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
