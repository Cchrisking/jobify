package com.mcverse.jobify.job.controller;

import com.mcverse.jobify.job.dto.JobPostResponse;
import com.mcverse.jobify.job.dto.UpdateJobAvailabilityRequest;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Jobs", description = "Browse and manage job postings — requires Bearer token")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(
            summary = "List job posts",
            description = "Public endpoint — no authentication required. " +
                    "Pass `available=true` to show only open positions (recommended for visitors and seekers). " +
                    "Pass `available=false` to see closed/filled posts. " +
                    "Omit the parameter to return all posts regardless of status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of job posts (may be empty)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobPostResponse.class)))),
    })
    @SecurityRequirements
    @GetMapping
    public List<JobPostResponse> getAllJobs(
            @Parameter(description = "Filter by availability — true = open only, false = closed only, omit = all",
                    example = "true")
            @RequestParam(required = false) Boolean available) {
        return jobService.getJobs(available);
    }

    @Operation(
            summary = "Create a new job post",
            description = "Persists a new JobPost. Returns the saved post with its generated ID. " +
                    "New posts are open (available=true) by default."
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

    @Operation(
            summary = "Open or close a job posting",
            description = "Sets the `available` flag on an existing job post. " +
                    "Use this to mark a position as filled (`available: false`) or re-open it (`available: true`). " +
                    "Ownership is not enforced at this stage — any authenticated user can update availability."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job post updated",
                    content = @Content(schema = @Schema(implementation = JobPostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Job post not found"),
    })
    @PatchMapping("/{id}/available")
    public JobPostResponse updateAvailability(
            @Parameter(description = "Job post ID", example = "3")
            @PathVariable Integer id,
            @RequestBody UpdateJobAvailabilityRequest request) {
        return jobService.updateAvailability(id, request.available());
    }
}
