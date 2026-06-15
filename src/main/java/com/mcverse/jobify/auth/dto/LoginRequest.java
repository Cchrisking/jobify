package com.mcverse.jobify.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for authentication")
public record LoginRequest(
        @Schema(description = "Registered username", example = "jane_doe")
        String username,

        @Schema(description = "Account password", example = "s3cur3P@ss")
        String password
) {}
