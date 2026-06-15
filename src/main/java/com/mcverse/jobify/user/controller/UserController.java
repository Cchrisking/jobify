package com.mcverse.jobify.user.controller;

import com.mcverse.jobify.user.dto.*;
import com.mcverse.jobify.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ── Seeker endpoints ─────────────────────────────────────────────────────

    @GetMapping("/seekers/me")
    public SeekerResponse getSeekerProfile(@AuthenticationPrincipal UserDetails principal) {
        return userService.getSeekerByUsername(principal.getUsername());
    }

    @GetMapping("/seekers/{id}")
    public SeekerResponse getSeekerById(@PathVariable String id) {
        return userService.getSeekerById(id);
    }

    @PutMapping("/seekers/me")
    public SeekerResponse updateSeekerProfile(@AuthenticationPrincipal UserDetails principal,
                                              @Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateSeeker(principal.getUsername(), request);
    }

    // ── Employer endpoints ────────────────────────────────────────────────────

    @GetMapping("/employers/me")
    public EmployerResponse getEmployerProfile(@AuthenticationPrincipal UserDetails principal) {
        return userService.getEmployerByUsername(principal.getUsername());
    }

    @GetMapping("/employers/{id}")
    public EmployerResponse getEmployerById(@PathVariable String id) {
        return userService.getEmployerById(id);
    }

    @PutMapping("/employers/me")
    public EmployerResponse updateEmployerProfile(@AuthenticationPrincipal UserDetails principal,
                                                  @Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateEmployer(principal.getUsername(), request);
    }

    // ── Company endpoints ─────────────────────────────────────────────────────

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(@AuthenticationPrincipal UserDetails principal,
                                         @Valid @RequestBody CompanyRequest request) {
        return userService.createCompany(principal.getUsername(), request);
    }

    @PutMapping("/companies/{id}")
    public CompanyResponse updateCompany(@AuthenticationPrincipal UserDetails principal,
                                         @PathVariable String id,
                                         @Valid @RequestBody CompanyRequest request) {
        return userService.updateCompany(principal.getUsername(), id, request);
    }
}
