package com.agencyflow.crm.security.jwt;

public interface JwtService {

    String generateToken(String email);

    String extractUsername(String token);

    boolean isTokenValid(String token, String email);
}
