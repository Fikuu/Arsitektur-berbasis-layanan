package com.fikri.auth_service.controller;

import com.fikri.auth_service.model.User;
import com.fikri.auth_service.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    // =====================================================
    // GET ALL USERS
    // =====================================================
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public List<User> getAllUsers() {

        log.info("GET_ALL_USERS");

        List<User> users = userRepository.findAll();

        log.info("GET_ALL_USERS_SUCCESS | totalUsers={}", users.size());

        return users;
    }

    // =====================================================
    // GET USER BY USERNAME
    // =====================================================
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {

        log.info("GET_USER | username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {

                    log.warn(
                            "GET_USER_FAILED | username={} | reason=User not found",
                            username);

                    return new RuntimeException("User tidak ditemukan");
                });

        log.info(
                "GET_USER_SUCCESS | username={} | role={}",
                user.getUsername(),
                user.getRole());

        return user;
    }
}