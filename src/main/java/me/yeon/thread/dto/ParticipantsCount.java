package me.yeon.thread.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.yeon.thread.domain.MessageType;

@AllArgsConstructor
@Getter
public class ParticipantsCount {

	private final MessageType messageType = MessageType.PARTICIPANT;
	private final int participantsCount;

}
