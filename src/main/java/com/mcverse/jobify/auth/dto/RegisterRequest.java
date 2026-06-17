package com.mcverse.jobify.auth.dto;

import com.mcverse.jobify.auth.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for account registration")
public record RegisterRequest(
        @Schema(description = "Unique username (used for login)", example = "jane_doe")
        @NotBlank String username,

        @Schema(description = "Password — minimum 8 characters recommended", example = "s3cur3P@ss")
        @NotBlank String password,

        @Schema(description = "SEEKER or EMPLOYER only — ADMIN is seeded and cannot be self-registered",
                example = "SEEKER", allowableValues = {"SEEKER", "EMPLOYER"})
        @NotNull Role role,

        @Schema(description = "First name", example = "Jane")
        @NotBlank String firstName,

        @Schema(description = "Last name", example = "Doe")
        @NotBlank String lastName
) {}
