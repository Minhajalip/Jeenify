package com.tracker.studentracker.controllers;

import com.tracker.studentracker.dto.AuthResponse;
import com.tracker.studentracker.dto.LoginRequest;
import com.tracker.studentracker.dto.RegisterRequest;
import com.tracker.studentracker.dto.StaffRegisterRequest;
import com.tracker.studentracker.models.Role;
import com.tracker.studentracker.models.User;
import com.tracker.studentracker.repository.UserRepository;
import com.tracker.studentracker.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // Public — students only, role is hardcoded server-side
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered");
            }
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPasswordHash(authService.hashPassword(request.getPassword()));
            user.setRole(Role.STUDENT); // hardcoded, client has no say
            userRepository.save(user);
            return ResponseEntity.ok("Student registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Admin only — create TEACHER or ADMIN accounts
    @PostMapping("/register/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerStaff(@RequestBody StaffRegisterRequest request) {
        try {
            if (request.getRole() == null || request.getRole() == Role.STUDENT) {
                return ResponseEntity.badRequest().body("Staff must be ADMIN or TEACHER");
            }
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered");
            }
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPasswordHash(authService.hashPassword(request.getPassword()));
            user.setRole(request.getRole());
            userRepository.save(user);
            return ResponseEntity.ok(request.getRole().name() + " registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }
}