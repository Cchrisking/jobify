package com.mcverse.jobify.admin;

import com.mcverse.jobify.auth.model.AppUser;
import com.mcverse.jobify.auth.repository.AuthUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Admin-only endpoints — require ADMIN role")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    private AuthUserRepository authUserRepository;

    public record UserSummary(Long id, String username, String role) {}

    @GetMapping("/users")
    @Operation(summary = "List all registered accounts")
    public List<UserSummary> getAllUsers() {
        return authUserRepository.findAll().stream()
                .map(u -> new UserSummary(u.getId(), u.getUsername(), u.getRole().name()))
                .toList();
    }
}
