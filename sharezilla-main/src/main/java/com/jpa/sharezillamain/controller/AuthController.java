package com.jpa.sharezillamain.controller;

import com.jpa.sharezillamain.dto.AuthResponse;
import com.jpa.sharezillamain.dto.LoginRequest;
import com.jpa.sharezillamain.dto.RegisterRequest;
import com.jpa.sharezillamain.model.AppUser;
import com.jpa.sharezillamain.repository.AppUserRepository;
import com.jpa.sharezillamain.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// controller/AuthController.java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AppUserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        AppUser user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(userDetails, user.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() == null) {
            return ResponseEntity.badRequest().body("Role is required");
        }
        user.setRole(request.getRole());

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully with role: " + request.getRole().name());
    }

    @GetMapping("/health")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("OK");
    }
}