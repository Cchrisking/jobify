package com.mcverse.jobify.user.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(@NotBlank String name) {}
