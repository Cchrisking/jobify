package com.mcverse.jobify.config;

import com.mcverse.jobify.auth.model.Role;
import com.mcverse.jobify.auth.repository.AuthUserRepository;
import com.mcverse.jobify.auth.security.UserDetailsServiceImpl;
import com.mcverse.jobify.user.dto.CompanyRequest;
import com.mcverse.jobify.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired private AuthUserRepository appUserRepo;
    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired private UserService userService;
    @Autowired private SeedService seedService;
    @Autowired private PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "password";

    @Override
    public void run(ApplicationArguments args) {
        if (appUserRepo.count() > 0) return; // already seeded

        seedUsers();
        seedService.seedJobPosts();
    }

    private void seedUsers() {
        // ── Seekers ───────────────────────────────────────────────────────────
        createUser("alice_s",  "Alice",  "Johnson",  Role.SEEKER);
        createUser("bob_s",    "Bob",    "Williams", Role.SEEKER);
        createUser("carol_s",  "Carol",  "Martinez", Role.SEEKER);

        // ── Employers ─────────────────────────────────────────────────────────
        createUser("techcorp",     "David", "Chen",   Role.EMPLOYER);
        createUser("startupxyz",   "Emma",  "Davis",  Role.EMPLOYER);
        createUser("financegroup", "Frank", "Wilson", Role.EMPLOYER);

        // ── Companies ─────────────────────────────────────────────────────────
        userService.createCompany("techcorp",     new CompanyRequest("TechCorp Ltd"));
        userService.createCompany("startupxyz",   new CompanyRequest("StartupXYZ Inc"));
        userService.createCompany("financegroup", new CompanyRequest("Finance Group SA"));
    }

    private void createUser(String username, String firstName, String lastName, Role role) {
        userDetailsService.save(username, passwordEncoder.encode(DEFAULT_PASSWORD), role);
        userService.createProfile(username, firstName, lastName, role);
    }
}
