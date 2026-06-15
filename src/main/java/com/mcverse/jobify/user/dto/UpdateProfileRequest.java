package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body to update a user profile's name fields")
public record UpdateProfileRequest(
        @Schema(description = "Updated first name", example = "Jane")
        @NotBlank String name,

        @Schema(description = "Updated last name", example = "Smith")
        @NotBlank String lastName
) {}
