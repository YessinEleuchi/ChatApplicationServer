package com.chatapplication.chat.service;

import org.springframework.stereotype.Service;

/**
 * Mock AI service implementation.
 */
@Service
public class MockAiService implements AiService {

    @Override
    public String generateResponse(String prompt) {
        return "This is a mock AI response to: \"" + prompt + "\"";
    }
}
