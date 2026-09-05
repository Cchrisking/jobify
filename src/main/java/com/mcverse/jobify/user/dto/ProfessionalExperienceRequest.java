package com.mcverse.jobify.user.dto;

import com.mcverse.jobify.model.EmploymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Request body to create or update a professional experience entry")
public record ProfessionalExperienceRequest(
        @Schema(description = "Job title", example = "Software Engineer")
        @NotBlank String jobTitle,

        @Schema(description = "Company name", example = "Acme Corp")
        @NotBlank String companyName,

        @Schema(description = "Location, optional", example = "Remote", nullable = true)
        String location,

        @Schema(description = "Employment type, optional", example = "FULL_TIME", nullable = true)
        EmploymentType employmentType,

        @Schema(description = "Start date", example = "2020-03-01")
        @NotNull LocalDate startDate,

        @Schema(description = "End date, omit or null if this is the current position", example = "2023-01-15", nullable = true)
        LocalDate endDate,

        @Schema(description = "Description of responsibilities/achievements, optional", nullable = true)
        String description
) {}
