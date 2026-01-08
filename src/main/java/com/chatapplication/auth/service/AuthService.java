package com.chatapplication.auth.service;

import com.chatapplication.auth.dto.AuthResponse;
import com.chatapplication.auth.dto.RegisterResponse;
import com.chatapplication.auth.model.RefreshTokenNode;
import com.chatapplication.auth.model.User;
import com.chatapplication.auth.repository.RefreshTokenRepository;
import com.chatapplication.auth.repository.UserRepository;
import com.chatapplication.common.exception.AppException;
import com.chatapplication.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponse register(String email, String password) {
        String normalizedEmail = email.toLowerCase().trim();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new AppException("Email already exists: " + normalizedEmail, HttpStatus.BAD_REQUEST);
        }

        String passwordHash = passwordEncoder.encode(password);
        User user = new User(UUID.randomUUID().toString(), normalizedEmail, passwordHash);
        User saved = userRepository.save(user);

        return new RegisterResponse(saved.getId(), saved.getEmail(), "User registered successfully");
    }

    public AuthResponse login(String email, String password) {
        String normalizedEmail = email.toLowerCase().trim();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new AppException("Invalid email or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new AppException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refreshTokenStr = UUID.randomUUID().toString();

        RefreshTokenNode refreshToken = new RefreshTokenNode(
                UUID.randomUUID().toString(),
                user.getId(),
                refreshTokenStr,
                Instant.now(),
                false);
        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken, refreshTokenStr);
    }

    public AuthResponse refresh(String refreshTokenStr) {
        RefreshTokenNode existingToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new AppException("Invalid refresh token", HttpStatus.UNAUTHORIZED));

        if (existingToken.isRevoked()) {
            throw new AppException("Refresh token has been revoked", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findById(existingToken.getUserId())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());

        // Simple flow: keep the same refresh token
        return new AuthResponse(accessToken, refreshTokenStr);
    }
}
