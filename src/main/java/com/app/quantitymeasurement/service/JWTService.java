package com.app.quantitymeasurement.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    public String generateToken(String username);
    public boolean validateToken(String token, UserDetails userDetails);

    public String extractUserName(String token);
}
