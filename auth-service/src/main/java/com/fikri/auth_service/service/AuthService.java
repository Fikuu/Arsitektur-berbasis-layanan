package com.fikri.auth_service.service;

import com.fikri.auth_service.dto.LoginRequest;
import com.fikri.auth_service.dto.RegisterRequest;
import com.fikri.auth_service.model.User;
import com.fikri.auth_service.repository.UserRepository;
import com.fikri.auth_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // public String authenticate(LoginRequest loginRequest) {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(
    // loginRequest.getUsername(),
    // loginRequest.getPassword()));

    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // return jwtUtil.generateToken(userDetails);
    // }

    public String authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(userDetails);
    }

    // public User register(RegisterRequest registerRequest) {
    // // Cek apakah username sudah ada
    // if (userRepository.existsByUsername(registerRequest.getUsername())) {
    // throw new RuntimeException("Username sudah terdaftar!");
    // }

    // // Cek apakah email sudah ada
    // if (userRepository.existsByEmail(registerRequest.getEmail())) {
    // throw new RuntimeException("Email sudah terdaftar!");
    // }

    // // Buat user baru
    // User user = new User();
    // user.setUsername(registerRequest.getUsername());
    // user.setEmail(registerRequest.getEmail());
    // user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    // user.setFullName(registerRequest.getFullName());
    // user.setRole("USER");
    // user.setIsActive(true);

    // return userRepository.save(user);
    // }

    // 🔥 REGISTER FIX
    public User register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username sudah terdaftar!");
        }

        // ⚠️ pastikan method ini ada di repository
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // 🔥 WAJIB
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setFullName(request.getFullName());
        user.setRole("USER");

        // ❌ HAPUS kalau gak ada di entity
        // user.setIsActive(true);

        return userRepository.save(user);
    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    public Boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}