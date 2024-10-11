package com.growthx.Task.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTServices {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
