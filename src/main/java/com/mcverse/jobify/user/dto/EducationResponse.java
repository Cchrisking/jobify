package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "An education entry on a seeker's profile")
public record EducationResponse(
        @Schema(description = "Education entry UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Institution name", example = "MIT")
        String institution,

        @Schema(description = "Degree obtained", example = "Bachelor of Science")
        String degree,

        @Schema(description = "Field of study", example = "Computer Science")
        String fieldOfStudy,

        @Schema(description = "Start date", example = "2018-09-01")
        LocalDate startDate,

        @Schema(description = "End date, null if ongoing", example = "2022-06-01", nullable = true)
        LocalDate endDate,

        @Schema(description = "Grade or GPA, optional", example = "3.8 GPA", nullable = true)
        String grade
) {}
