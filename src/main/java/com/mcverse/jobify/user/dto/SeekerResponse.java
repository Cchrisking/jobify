package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Job seeker profile")
public record SeekerResponse(
        @Schema(description = "UUID profile identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Username", example = "jane_doe")
        String username,

        @Schema(description = "First name", example = "Jane")
        String name,

        @Schema(description = "Last name", example = "Doe")
        String lastName,

        @Schema(description = "Profile creation timestamp (ISO-8601)", example = "2024-01-15T10:30:00")
        LocalDateTime creationDate,

        @Schema(description = "Whether the seeker works as an independent contractor", example = "false")
        boolean isIndependent
) {}
