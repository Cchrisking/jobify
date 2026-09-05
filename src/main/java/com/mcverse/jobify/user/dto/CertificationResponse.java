package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "A certification entry on a seeker's profile")
public record CertificationResponse(
        @Schema(description = "Certification entry UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Certification name", example = "AWS Certified Solutions Architect")
        String name,

        @Schema(description = "Issuing organization", example = "Amazon Web Services")
        String issuingOrganization,

        @Schema(description = "Date issued", example = "2023-05-10")
        LocalDate issueDate,

        @Schema(description = "Expiration date, null if it does not expire", example = "2026-05-10", nullable = true)
        LocalDate expirationDate,

        @Schema(description = "Credential ID, optional", example = "AWS-SAA-123456", nullable = true)
        String credentialId,

        @Schema(description = "Credential verification URL, optional", example = "https://aws.amazon.com/verify/123456", nullable = true)
        String credentialUrl
) {}
