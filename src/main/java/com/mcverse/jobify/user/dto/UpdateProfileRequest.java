package com.mcverse.jobify.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(@NotBlank String name, @NotBlank String lastName) {}
