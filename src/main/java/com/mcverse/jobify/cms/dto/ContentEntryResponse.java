package com.mcverse.jobify.cms.dto;

import com.mcverse.jobify.cms.model.ContentCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "A single piece of admin-editable front-end content")
public record ContentEntryResponse(
        String key,
        ContentCategory category,
        String value,
        LocalDateTime updatedAt
) {}
