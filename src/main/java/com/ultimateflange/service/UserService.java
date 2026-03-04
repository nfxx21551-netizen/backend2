package com.ultimateflange.service;

import com.ultimateflange.dto.LoginRequest;
import com.ultimateflange.dto.SignupRequest;
import com.ultimateflange.dto.AuthResponse;
import com.ultimateflange.model.User;
import com.ultimateflange.repository.UserRepository;
import com.ultimateflange.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;




    public AuthResponse signup(SignupRequest request) {
        // Check if user exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, null, null, null, "Email already exists");
        }

        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompany(request.getCompany());
        user.setPhone(request.getPhone());
        user.setIndustry(request.getIndustry());
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                token,
                "Signup successful"
        );
    }


    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(null, null, null, null, "Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                token,
                "Login successful"
        );
    }
}