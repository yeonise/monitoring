package me.yeon.thread.event;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.application.RoomService;

@Component
@RequiredArgsConstructor
public class SocketEventListener {

	private final RoomService roomService;

	@EventListener
	public void handleSubscribeEvent(SessionSubscribeEvent event) {
		roomService.sendParticipantsCount();
	}

	@EventListener
	public void handleDisconnectEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionCode = headerAccessor.getSessionId();

		roomService.leave(sessionCode);
	}

}
