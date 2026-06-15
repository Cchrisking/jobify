package com.mcverse.jobify.auth.dto;

import com.mcverse.jobify.auth.model.Role;

public record RegisterRequest(String username, String password, Role role) {}
