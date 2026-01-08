package com.chatapplication.chat.service;

import com.chatapplication.auth.model.User;
import com.chatapplication.chat.dto.ChatResponse;
import com.chatapplication.chat.dto.MessageDto;
import com.chatapplication.chat.model.Message;
import com.chatapplication.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final AiService aiService;

    public ChatService(MessageRepository messageRepository, AiService aiService) {
        this.messageRepository = messageRepository;
        this.aiService = aiService;
    }

    public ChatResponse sendMessage(User sender, String prompt) {
        String response = aiService.generateResponse(prompt);

        Message message = new Message(
                sender,
                prompt,
                response,
                Instant.now());

        Message saved = messageRepository.save(message);

        return new ChatResponse(
                saved.getId(),
                saved.getPrompt(),
                saved.getResponse(),
                saved.getTimestamp());
    }

    public List<MessageDto> getHistory(String userId) {
        return messageRepository.findByUserIdOrderByTimestampAsc(userId)
                .stream()
                .map(m -> new MessageDto(
                        m.getId(),
                        m.getPrompt(),
                        m.getResponse(),
                        m.getTimestamp()))
                .collect(Collectors.toList());
    }
}
