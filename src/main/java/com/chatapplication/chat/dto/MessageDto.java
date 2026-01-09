package com.chatapplication.chat.dto;

import com.chatapplication.chat.model.Message;

import java.time.Instant;

public record MessageDto(
        String id,
        String prompt,
        String response,
        Instant timestamp) {
    public static MessageDto fromEntity(Message m) {
        return new MessageDto(m.getId(), m.getPrompt(), m.getResponse(), m.getTimestamp());
    }
}
