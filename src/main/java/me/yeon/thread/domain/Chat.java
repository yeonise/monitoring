package me.yeon.thread.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Chat {

	private final MessageType messageType = MessageType.CHAT;

	@Getter
	private final String content;

	@Getter
	private final String createdAt;

	@Getter
	private final String sender;

}
