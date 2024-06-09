package me.yeon.thread.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.application.ChatService;
import me.yeon.thread.domain.Chat;
import me.yeon.thread.dto.ChatContent;
import me.yeon.thread.dto.NewChat;

@RestController
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@GetMapping("/chats")
	public ResponseEntity<List<Chat>> fetchAll() {
		return ResponseEntity.ok().body(chatService.fetchAll());
	}

	@MessageMapping("/chats")
	public void sendChat(@Header(StompHeaderAccessor.SESSION_ID_HEADER) String sessionCode, ChatContent chatContent) {
		chatService.send(new NewChat(chatContent.getContent(), LocalDateTime.now().toString(), sessionCode));
	}

}
