package com.mcverse.jobify.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A job posting as returned by the API")
public record JobPostResponse(
        @Schema(description = "Unique job post identifier", example = "1")
        Integer postId,

        @Schema(description = "Job title", example = "Senior Java Developer")
        String jobTitle,

        @Schema(description = "Full job description including responsibilities and requirements",
                example = "We are looking for a Senior Java Developer with 5+ years of experience...")
        String jobDescription,

        @Schema(description = "Employer rating on a 0.0–5.0 scale", example = "4.2")
        double jobRating,

        @Schema(description = "Hourly pay rate in USD", example = "75.00")
        double hourlyRate,

        @Schema(description = "Username of the employer who created this post; null if unassigned",
                example = "acme_corp", nullable = true)
        String employerUsername
) {}
