package com.mcverse.jobify.job.dto;

import com.mcverse.jobify.model.EmploymentType;
import com.mcverse.jobify.model.WorkMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Request body to create or fully replace a job posting")
public record CreateJobRequest(
        @Schema(description = "Job title", example = "Senior Java Developer")
        @NotBlank(message = "is required")
        @Size(max = JobLimits.TITLE_MAX, message = "must be at most " + JobLimits.TITLE_MAX + " characters")
        String jobTitle,

        @Schema(description = "Rich-text HTML. Sanitized on the server: only p, br, ul, ol, li, strong, em, h2, "
                + "h3 and a[href] survive.", example = "<p>We are hiring.</p>")
        @NotBlank(message = "is required")
        @Size(max = JobLimits.DESCRIPTION_MAX,
                message = "must be at most " + JobLimits.DESCRIPTION_MAX + " characters")
        String jobDescription,

        @Schema(description = "Rating on a 0.0–5.0 scale; defaults to 0 when omitted", example = "4.2")
        @DecimalMin(value = "0.0", message = "must be between 0 and 5")
        @DecimalMax(value = "5.0", message = "must be between 0 and 5")
        Double jobRating,

        @Schema(description = "Hourly pay rate in USD; defaults to 0 when omitted", example = "75.00")
        @DecimalMin(value = "0.0", message = "must not be negative")
        Double hourlyRate,

        @Schema(description = "Free-text work location", example = "Berlin, DE", nullable = true)
        @Size(max = JobLimits.LOCATION_MAX, message = "must be at most " + JobLimits.LOCATION_MAX + " characters")
        String location,

        @Schema(nullable = true) WorkMode workMode,

        @Schema(nullable = true) EmploymentType employmentType,

        @Schema(description = "Names of required skills. Unknown names are added to the shared catalog.",
                example = "[\"Java\", \"Spring Boot\"]", nullable = true)
        @Size(max = JobLimits.SKILLS_MAX, message = "must have at most " + JobLimits.SKILLS_MAX + " entries")
        List<@NotBlank(message = "must not be blank")
        @Size(max = JobLimits.SKILL_NAME_MAX,
                message = "must be at most " + JobLimits.SKILL_NAME_MAX + " characters") String> requiredSkills
) {}
