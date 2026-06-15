package com.mcverse.jobify.auth.service;

import com.mcverse.jobify.auth.dto.LoginRequest;
import com.mcverse.jobify.auth.dto.RegisterRequest;
import com.mcverse.jobify.auth.dto.TokenResponse;
import com.mcverse.jobify.auth.security.JwtService;
import com.mcverse.jobify.auth.security.UserDetailsServiceImpl;
import com.mcverse.jobify.config.JwtConfig;
import com.mcverse.jobify.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired private UserService userService;
    @Autowired private JwtService jwtService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtConfig jwtConfig;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        userDetailsService.save(request.username(), passwordEncoder.encode(request.password()), request.role());
        userService.createProfile(request.username(), request.firstName(), request.lastName(), request.role());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        return new TokenResponse(jwtService.generateToken(userDetails), jwtConfig.getExpiration());
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        return new TokenResponse(jwtService.generateToken(userDetails), jwtConfig.getExpiration());
    }
}
