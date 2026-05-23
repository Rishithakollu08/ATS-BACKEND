package com.ats.atsbackend.controller;

import com.ats.atsbackend.entity.User;
import com.ats.atsbackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println("REGISTER HIT: " + user.getEmail()); // ← add this
        try {
            String result = authService.register(user);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            System.out.println("REGISTER ERROR: " + e.getMessage()); // ← add this
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            String token = authService.login(email, password);
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", email
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}