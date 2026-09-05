package com.mcverse.jobify.cms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Bulk content update — content key to new value")
public record UpdateContentRequest(
        Map<String, String> values
) {}
