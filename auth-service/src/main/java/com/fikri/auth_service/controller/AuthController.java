package com.fikri.auth_service.controller;

import com.fikri.auth_service.dto.AuthResponse;
import com.fikri.auth_service.dto.LoginRequest;
import com.fikri.auth_service.dto.RegisterRequest;
import com.fikri.auth_service.model.User;
import com.fikri.auth_service.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // =====================================================
    // LOGIN
    // =====================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        log.info(
                "LOGIN | username={}",
                loginRequest.getUsername());

        try {

            String token = authService.authenticate(loginRequest);

            User user = authService.getCurrentUser(loginRequest.getUsername());

            log.info(
                    "LOGIN_SUCCESS | username={} | role={}",
                    user.getUsername(),
                    user.getRole());

            AuthResponse response = new AuthResponse(
                    token,
                    "Bearer",
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole(),
                    86400000L);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.warn(
                    "LOGIN_FAILED | username={} | reason={}",
                    loginRequest.getUsername(),
                    e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login gagal: " + e.getMessage());
        }
    }

    // =====================================================
    // REGISTER
    // =====================================================
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

        log.info(
                "REGISTER | username={} | email={}",
                registerRequest.getUsername(),
                registerRequest.getEmail());

        try {

            User user = authService.register(registerRequest);

            log.info(
                    "REGISTER_SUCCESS | username={} | role={}",
                    user.getUsername(),
                    user.getRole());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(user);

        } catch (Exception e) {

            log.warn(
                    "REGISTER_FAILED | username={} | reason={}",
                    registerRequest.getUsername(),
                    e.getMessage());

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // VALIDATE TOKEN
    // =====================================================
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        log.info("TOKEN_VALIDATE");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            log.warn("TOKEN_VALIDATE_FAILED | Authorization header missing");

            return ResponseEntity.badRequest()
                    .body("Authorization header missing");
        }

        String token = authHeader.substring(7);

        Boolean isValid = authService.validateToken(token);

        log.info(
                "TOKEN_VALIDATE_SUCCESS | valid={}",
                isValid);

        return ResponseEntity.ok(isValid);
    }
}