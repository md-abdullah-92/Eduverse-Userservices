package com.eduverse.userservices.dto;

import com.eduverse.userservices.model.User;

public class AuthResponse {
    
    private String token;
    private User user;
    private String message;
    
    // Constructors
    public AuthResponse() {}
    
    public AuthResponse(String token, User user, String message) {
        this.token = token;
        this.user = user;
        this.message = message;
    }
    
    // Builder pattern
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }
    
    public static class AuthResponseBuilder {
        private String token;
        private User user;
        private String message;
        
        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }
        
        public AuthResponseBuilder user(User user) {
            this.user = user;
            return this;
        }
        
        public AuthResponseBuilder message(String message) {
            this.message = message;
            return this;
        }
        
        public AuthResponse build() {
            return new AuthResponse(token, user, message);
        }
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
