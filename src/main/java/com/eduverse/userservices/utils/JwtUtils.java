package com.eduverse.userservices.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {

    // Method to generate JWT token
    public String generateToken(String id) {
        byte[] key = ("sust_knownssust_knownssust_knownssust_knownssust_knownssust_knownssust_knownssust_knownssust_kn")
                .getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(key);

        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .signWith(secretKey)
                .compact();
    }

    // Method to validate JWT token and retrieve user ID
    public String validateToken(String token) {
        byte[] key = "sust_knownssust_knownssust_knownssust_knownssust_knownssust_knownssust_knownssust_knownssust_kn"
                .getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(key);

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
