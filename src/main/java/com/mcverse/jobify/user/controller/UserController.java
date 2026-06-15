package com.mcverse.jobify.user.controller;

import com.mcverse.jobify.user.dto.*;
import com.mcverse.jobify.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Seeker and Employer profile management — requires Bearer token")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ── Seeker endpoints ──────────────────────────────────────────────────────

    @Operation(summary = "Get own seeker profile",
            description = "Returns the seeker profile of the currently authenticated user. Only accessible if the token belongs to a SEEKER account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seeker profile",
                    content = @Content(schema = @Schema(implementation = SeekerResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Seeker profile not found for this username"),
    })
    @GetMapping("/seekers/me")
    public SeekerResponse getSeekerProfile(@AuthenticationPrincipal UserDetails principal) {
        return userService.getSeekerByUsername(principal.getUsername());
    }

    @Operation(summary = "Get seeker by ID",
            description = "Returns any seeker profile by its UUID. Accessible by all authenticated users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seeker profile",
                    content = @Content(schema = @Schema(implementation = SeekerResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Seeker not found"),
    })
    @GetMapping("/seekers/{id}")
    public SeekerResponse getSeekerById(
            @Parameter(description = "Seeker UUID", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String id) {
        return userService.getSeekerById(id);
    }

    @Operation(summary = "Update own seeker profile",
            description = "Updates the first and last name of the authenticated seeker's profile.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated seeker profile",
                    content = @Content(schema = @Schema(implementation = SeekerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error — name or lastName is blank"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Seeker profile not found"),
    })
    @PutMapping("/seekers/me")
    public SeekerResponse updateSeekerProfile(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateSeeker(principal.getUsername(), request);
    }

    // ── Employer endpoints ────────────────────────────────────────────────────

    @Operation(summary = "Get own employer profile",
            description = "Returns the employer profile of the currently authenticated user. Only accessible if the token belongs to an EMPLOYER account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employer profile (company may be null if not yet created)",
                    content = @Content(schema = @Schema(implementation = EmployerResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Employer profile not found"),
    })
    @GetMapping("/employers/me")
    public EmployerResponse getEmployerProfile(@AuthenticationPrincipal UserDetails principal) {
        return userService.getEmployerByUsername(principal.getUsername());
    }

    @Operation(summary = "Get employer by ID",
            description = "Returns any employer profile by its UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employer profile",
                    content = @Content(schema = @Schema(implementation = EmployerResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Employer not found"),
    })
    @GetMapping("/employers/{id}")
    public EmployerResponse getEmployerById(
            @Parameter(description = "Employer UUID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            @PathVariable String id) {
        return userService.getEmployerById(id);
    }

    @Operation(summary = "Update own employer profile",
            description = "Updates the first and last name of the authenticated employer's profile.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated employer profile",
                    content = @Content(schema = @Schema(implementation = EmployerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Employer profile not found"),
    })
    @PutMapping("/employers/me")
    public EmployerResponse updateEmployerProfile(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateEmployer(principal.getUsername(), request);
    }

    // ── Company endpoints ─────────────────────────────────────────────────────

    @Operation(summary = "Create a company for the authenticated employer",
            description = "An employer can only have one company. Returns 422 if a company already exists — use the update endpoint instead.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Company created",
                    content = @Content(schema = @Schema(implementation = CompanyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Employer profile not found"),
            @ApiResponse(responseCode = "422", description = "Employer already has a company"),
    })
    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody CompanyRequest request) {
        return userService.createCompany(principal.getUsername(), request);
    }

    @Operation(summary = "Update the authenticated employer's company",
            description = "Only the employer who owns the company can update it. Returns 422 if the ID does not match the caller's company.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company updated",
                    content = @Content(schema = @Schema(implementation = CompanyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Employer profile not found"),
            @ApiResponse(responseCode = "422", description = "Caller does not own this company"),
    })
    @PutMapping("/companies/{id}")
    public CompanyResponse updateCompany(
            @AuthenticationPrincipal UserDetails principal,
            @Parameter(description = "Company UUID", example = "7f3b2c4a-1234-5678-abcd-ef0123456789")
            @PathVariable String id,
            @Valid @RequestBody CompanyRequest request) {
        return userService.updateCompany(principal.getUsername(), id, request);
    }
}
