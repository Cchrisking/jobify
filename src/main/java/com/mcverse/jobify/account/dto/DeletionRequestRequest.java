package com.mcverse.jobify.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to ask an admin to delete the authenticated account")
public record DeletionRequestRequest(
        @Schema(description = "Optional reason for the request", example = "No longer job hunting", nullable = true)
        String reason
) {}
