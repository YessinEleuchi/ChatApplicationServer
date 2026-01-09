package com.chatapplication.auth.dto;

public record AuthResponse(
    String accessToken,
    String refreshToken) {
}
