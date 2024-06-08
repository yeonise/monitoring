package me.yeon.thread.application;

import static me.yeon.thread.util.AppConstants.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.domain.Room;
import me.yeon.thread.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomService {

	private final RoomRepository roomRepository;

	public synchronized int enter() {
		Room room = roomRepository.findById(DEFAULT_ROOM_CODE)
			.orElseThrow(() -> new RuntimeException("The room cannot be found."));

		int participantsCount = room.enter();
		roomRepository.save(room);

		return participantsCount;
	}

	public synchronized int leave() {
		Room room = roomRepository.findById(DEFAULT_ROOM_CODE)
			.orElseThrow(() -> new RuntimeException("The room cannot be found."));

		int participantsCount = room.leave();
		roomRepository.save(room);

		return participantsCount;
	}

}
