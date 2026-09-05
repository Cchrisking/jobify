package com.mcverse.jobify.account.dto;

import com.mcverse.jobify.account.model.DeletionRequestStatus;
import com.mcverse.jobify.auth.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "An account deletion request")
public record DeletionRequestResponse(
        String id,
        String username,
        Role requesterRole,
        String reason,
        DeletionRequestStatus status,
        LocalDateTime requestedAt,
        LocalDateTime resolvedAt,
        String resolutionNote
) {}
