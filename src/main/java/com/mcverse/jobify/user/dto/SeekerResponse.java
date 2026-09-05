package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

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
        boolean isIndependent,

        @Schema(description = "The seeker's uploaded resume, if any", nullable = true)
        CvResponse cv,

        @Schema(description = "Education entries")
        List<EducationResponse> educations,

        @Schema(description = "Certification entries")
        List<CertificationResponse> certifications,

        @Schema(description = "Professional experience entries")
        List<ProfessionalExperienceResponse> experiences,

        @Schema(description = "Skills")
        List<SeekerSkillResponse> skills
) {}
