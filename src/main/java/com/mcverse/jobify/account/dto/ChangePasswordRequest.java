package com.mcverse.jobify.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body to change the authenticated account's password")
public record ChangePasswordRequest(
        @Schema(description = "The account's current password", example = "hunter2")
        @NotBlank String currentPassword,

        @Schema(description = "The new password (min 8 characters)", example = "correct-horse-battery")
        @NotBlank @Size(min = 8, message = "New password must be at least 8 characters") String newPassword
) {}
