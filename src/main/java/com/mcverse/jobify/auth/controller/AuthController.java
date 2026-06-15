package com.mcverse.jobify.auth.controller;

import com.mcverse.jobify.auth.dto.LoginRequest;
import com.mcverse.jobify.auth.dto.RegisterRequest;
import com.mcverse.jobify.auth.dto.TokenResponse;
import com.mcverse.jobify.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
