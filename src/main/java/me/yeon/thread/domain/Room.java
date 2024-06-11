package me.yeon.thread.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RedisHash(value = "room")
@AllArgsConstructor
public class Room {

	@Id
	private final String code;

	@Getter
	private final String title;

	@Getter
	private final int capacity;

	@Getter
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
