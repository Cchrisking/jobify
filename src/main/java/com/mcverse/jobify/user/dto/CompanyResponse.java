package com.mcverse.jobify.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Company linked to an employer profile")
public record CompanyResponse(
        @Schema(description = "UUID company identifier", example = "7f3b2c4a-1234-5678-abcd-ef0123456789")
        String id,

        @Schema(description = "Company name", example = "Acme Corporation")
        String name
) {}
