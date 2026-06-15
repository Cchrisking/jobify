package com.mcverse.jobify.auth.dto;

public record TokenResponse(String token, String type, long expiresIn, String username, String role) {

    public TokenResponse(String token, long expiresIn, String username, String role) {
        this(token, "Bearer", expiresIn, username, role);
    }
}
