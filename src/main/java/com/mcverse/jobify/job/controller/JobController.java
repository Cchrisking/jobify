package com.mcverse.jobify.job.controller;

import com.mcverse.jobify.job.dto.JobPostResponse;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Jobs", description = "Browse and create job postings — requires Bearer token")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(
            summary = "List all job posts",
            description = "Returns every job posting stored in the system. No pagination at this stage."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of job posts (may be empty)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobPostResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
    })
    @GetMapping
    public List<JobPostResponse> getAllJobs() {
        return jobService.getJobs();
    }

    @Operation(
            summary = "Create a new job post",
            description = "Persists a new JobPost. The employer association must be set on the entity before saving. Returns the saved post with its generated ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Job post created",
                    content = @Content(schema = @Schema(implementation = JobPostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobPostResponse addJob(@RequestBody JobPost jobPost) {
        return jobService.addJob(jobPost);
    }
}
