package me.yeon.thread.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Chat {

	private final MessageType messageType = MessageType.CHAT;
	private String content;
	private String createdAt;
	private String sender;

}
