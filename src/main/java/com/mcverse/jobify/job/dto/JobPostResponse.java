package com.mcverse.jobify.job.dto;

import com.mcverse.jobify.model.EmploymentType;
import com.mcverse.jobify.model.WorkMode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "A job posting as returned by the API")
public record JobPostResponse(
        @Schema(description = "Unique job post identifier", example = "1")
        Integer postId,

        @Schema(description = "Job title", example = "Senior Java Developer")
        String jobTitle,

        @Schema(description = "Full job description, stored as sanitized rich-text HTML " +
                "(headings, bold, italic, bullet/numbered lists, emoji).",
                example = "<p>We are looking for a <strong>Senior Java Developer</strong>...</p>")
        String jobDescription,

        @Schema(description = "Employer rating on a 0.0–5.0 scale", example = "4.2")
        double jobRating,

        @Schema(description = "Hourly pay rate in USD", example = "75.00")
        double hourlyRate,

        @Schema(description = "Username of the employer who created this post; null if unassigned",
                example = "acme_corp", nullable = true)
        String employerUsername,

        @Schema(description = "Whether the position is still open and accepting applications. " +
                "Use this to hide closed/filled positions in the UI.", example = "true")
        boolean available,

        @Schema(description = "Free-text work location, e.g. city/region", example = "Berlin, DE", nullable = true)
        String location,

        @Schema(description = "Where the work happens", nullable = true)
        WorkMode workMode,

        @Schema(description = "Contract type", nullable = true)
        EmploymentType employmentType,

        @Schema(description = "When this post was created; null for posts that predate this field", nullable = true)
        LocalDateTime createdAt,

        @Schema(description = "Names of skills required for this position, drawn from the shared skill catalog")
        List<String> requiredSkills
) {}
