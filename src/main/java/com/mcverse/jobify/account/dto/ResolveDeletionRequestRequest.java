package com.mcverse.jobify.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body when an admin rejects a deletion request")
public record ResolveDeletionRequestRequest(
        @Schema(description = "Optional note explaining the decision", example = "Account has an active dispute", nullable = true)
        String note
) {}
