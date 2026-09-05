package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Metadata for a seeker's uploaded resume")
public record CvResponse(
        @Schema(description = "The file name as originally uploaded", example = "Jane_Doe_Resume.pdf")
        String originalFileName,

        @Schema(description = "MIME type of the uploaded file", example = "application/pdf")
        String fileType,

        @Schema(description = "When the current resume was uploaded (ISO-8601)", example = "2026-08-22T18:04:00")
        LocalDateTime uploadedAt
) {}
