package me.yeon.thread.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Room {

	private final int code;

	@Getter
	private final String title;

	@Getter
	private final int capacity;

	private int participantsCount;

	public int enter() {
		if (capacity <= participantsCount) {
			throw new RuntimeException("You cannot enter because the room is full.");
		}

		return ++participantsCount;
	}

	public int leave() {
		return --participantsCount;
	}

}
