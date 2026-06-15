package com.mcverse.jobify.user.dto;

import java.time.LocalDateTime;

public record SeekerResponse(
        String id,
        String username,
        String name,
        String lastName,
        LocalDateTime creationDate,
        boolean isIndependent
) {}
