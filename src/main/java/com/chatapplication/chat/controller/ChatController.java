package com.chatapplication.chat.controller;

import com.chatapplication.chat.dto.ChatRequest;
import com.chatapplication.chat.dto.ChatResponse;
import com.chatapplication.chat.dto.MessageDto;
import com.chatapplication.chat.service.ChatService;
import com.chatapplication.security.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> sendMessage(
            @Valid @RequestBody ChatRequest request,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        ChatResponse response = chatService.sendMessage(user.userId(), request.prompt());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public List<MessageDto> history(Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        return chatService.getHistory(user.userId());
    }
}
