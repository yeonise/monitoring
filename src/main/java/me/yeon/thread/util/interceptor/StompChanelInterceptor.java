package me.yeon.thread.util.interceptor;

import static me.yeon.thread.util.AppConstants.*;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompChanelInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

			if (sessionAttributes != null) {
				String sessionCode = sessionAttributes.get(SESSION_ATTRIBUTE_KEY).toString();

				if (sessionCode != null) {
					accessor.addNativeHeader(StompHeaderAccessor.SESSION_ID_HEADER, sessionCode);
				}
			}
		}

		return message;
	}

}
