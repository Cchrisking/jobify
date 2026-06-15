package com.mcverse.jobify.user.dto;

import java.time.LocalDateTime;

public record EmployerResponse(
        String id,
        String username,
        String name,
        String lastName,
        LocalDateTime creationDate,
        CompanyResponse company
) {}
