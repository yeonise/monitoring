package me.yeon.thread.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.yeon.thread.domain.MessageType;

@AllArgsConstructor
@Getter
public class ThreadStatus {

	private final MessageType messageType = MessageType.MONITOR;
	private final String content;

}
