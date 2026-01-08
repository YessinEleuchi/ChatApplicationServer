package com.chatapplication.chat.dto;

import java.time.Instant;

public record MessageDto(
        String id,
        String prompt,
        String response,
        Instant timestamp) {
}
