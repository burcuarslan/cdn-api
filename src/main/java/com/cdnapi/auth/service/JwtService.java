package com.cdnapi.auth.service;

import com.cdnapi.user.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateToken(Map<String, Object> extraClaims, User user);
    String generateToken(User user);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String extractEmail(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
