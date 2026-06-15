package com.mcverse.jobify.job.dto;

public record JobPostResponse(
        Integer postId,
        String jobTitle,
        String jobDescription,
        double jobRating,
        double hourlyRate,
        String employerUsername
) {}
