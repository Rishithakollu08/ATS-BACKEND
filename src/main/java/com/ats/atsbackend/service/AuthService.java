package com.ats.atsbackend.service;

import com.ats.atsbackend.entity.User;
import com.ats.atsbackend.repository.UserRepository;
import com.ats.atsbackend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(User user) {

        System.out.println("Inside register service");

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token;
        token = jwtService.generateToken(user.getEmail());

        return token;
    }
}