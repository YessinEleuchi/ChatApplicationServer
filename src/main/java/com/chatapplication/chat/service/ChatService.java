package com.chatapplication.chat.service;

import com.chatapplication.auth.model.User;
import com.chatapplication.auth.repository.UserRepository;
import com.chatapplication.chat.dto.ChatResponse;
import com.chatapplication.chat.dto.MessageDto;
import com.chatapplication.chat.model.Message;
import com.chatapplication.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final AiService aiService;
    private final UserRepository userRepository;

    public ChatService(MessageRepository messageRepository,
            AiService aiService,
            UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.aiService = aiService;
        this.userRepository = userRepository;
    }

    public ChatResponse sendMessage(String userId, String prompt) {
        User sender = userRepository.findUserById(userId);
        if (sender == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

        String aiResponse = aiService.generateResponse(prompt);
        Message message = new Message(sender, prompt, aiResponse, Instant.now());
        Message saved = messageRepository.save(message);

        return new ChatResponse(saved.getId(), saved.getPrompt(), saved.getResponse(), saved.getTimestamp());
    }

    public List<MessageDto> getHistory(String userId) {
        return messageRepository.findHistoryByUserId(userId)
                .stream()
                .map(MessageDto::fromEntity)
                .toList();
    }
}
