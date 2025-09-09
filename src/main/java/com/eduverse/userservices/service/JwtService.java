package com.eduverse.userservices.service;

import com.eduverse.userservices.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());
        extraClaims.put("role", user.getRole().toString());
        extraClaims.put("name", user.getName());
        extraClaims.put("isVerified", user.getIs_verified());
        return generateToken(extraClaims, user.getEmail());
    }
    
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtExpiration);
    }
    
    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKey hmacKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(hmacKey)
                .compact();
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    private Claims extractAllClaims(String token) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKey hmacKey = Keys.hmacShaKeyFor(keyBytes);
        try {
            return Jwts.parser()
                    .verifyWith(hmacKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", Long.class);
    }
    
    public String extractUserRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }
}
