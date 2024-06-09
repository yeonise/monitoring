package me.yeon.thread.application;

import static me.yeon.thread.util.AppConstants.*;

import java.util.List;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.domain.Chat;
import me.yeon.thread.domain.User;
import me.yeon.thread.dto.NewChat;
import me.yeon.thread.repository.ChatRepository;
import me.yeon.thread.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final SimpMessageSendingOperations operations;
	private final UserRepository userRepository;
	private final ChatRepository chatRepository;

	public void send(NewChat newChat) {
		User sender = userRepository.findById(newChat.getSenderSessionCode())
			.orElseThrow(() -> new RuntimeException("User session cannot be found."));

		Chat chat = new Chat(newChat.getContent(), newChat.getCreatedAt(), sender.getNickname());
		operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, chat);
		chatRepository.save(chat);
	}

	public List<Chat> fetchAll() {
		return chatRepository.findAll();
	}

}
