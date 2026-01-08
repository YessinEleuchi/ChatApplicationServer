package com.chatapplication.auth.dto;

import com.chatapplication.chat.dto.MessageDto;

import java.util.List;

public record AuthResponse(
        String accessToken,
        String refreshToken,
      List<MessageDto> history) {
}
