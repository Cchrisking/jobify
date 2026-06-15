package com.mcverse.jobify.auth.dto;

import com.mcverse.jobify.auth.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull  Role role,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
