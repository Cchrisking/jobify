package com.mcverse.jobify.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Successful authentication response — store the token and use it as a Bearer header on every subsequent request")
public record TokenResponse(
        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Token type, always 'Bearer'", example = "Bearer")
        String type,

        @Schema(description = "Token lifetime in milliseconds from time of issuance", example = "86400000")
        long expiresIn,

        @Schema(description = "Username of the authenticated user", example = "jane_doe")
        String username,

        @Schema(description = "Role of the authenticated user", example = "SEEKER",
                allowableValues = {"SEEKER", "EMPLOYER"})
        String role
) {
    public TokenResponse(String token, long expiresIn, String username, String role) {
        this(token, "Bearer", expiresIn, username, role);
    }
}
