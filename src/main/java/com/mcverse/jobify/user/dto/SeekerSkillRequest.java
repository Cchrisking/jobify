package com.mcverse.jobify.user.dto;

import com.mcverse.jobify.model.ProficiencyLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to add or update a skill on a seeker's profile")
public record SeekerSkillRequest(
        @Schema(description = "Skill name — matched case-insensitively against the skill catalog, " +
                "creating a new catalog entry if none exists", example = "Java")
        @NotBlank String skillName,

        @Schema(description = "Skill category, optional — only used when the skill is newly created",
                example = "Programming Language", nullable = true)
        String category,

        @Schema(description = "Self-reported proficiency level", example = "ADVANCED")
        @NotNull ProficiencyLevel proficiencyLevel,

        @Schema(description = "Years of experience with this skill, optional", example = "4.5", nullable = true)
        Double yearsOfExperience
) {}
