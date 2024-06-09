package me.yeon.thread.application;

import static me.yeon.thread.util.AppConstants.*;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.domain.Room;
import me.yeon.thread.dto.ParticipantsCount;
import me.yeon.thread.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomService {

	private final SimpMessageSendingOperations operations;
	private final RoomRepository roomRepository;

	public synchronized int enter(String sessionCode) {
		Room room = roomRepository.findById(DEFAULT_ROOM_CODE)
			.orElseThrow(() -> new RuntimeException("The room cannot be found."));

		if (roomRepository.isParticipant(sessionCode)) {
			throw new RuntimeException("The user is already participating.");
		}

		int participantsCount = room.enter();
		roomRepository.save(room);
		roomRepository.addParticipant(sessionCode);

		return participantsCount;
	}

	public synchronized int leave(String sessionCode) {
		Room room = roomRepository.findById(DEFAULT_ROOM_CODE)
			.orElseThrow(() -> new RuntimeException("The room cannot be found."));

		int participantsCount = room.leave();
		roomRepository.save(room);
		roomRepository.removeParticipant(sessionCode);

		operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, new ParticipantsCount(participantsCount));

		return participantsCount;
	}

	public void sendParticipantsCount() {
		Room room = roomRepository.findById(DEFAULT_ROOM_CODE)
			.orElseThrow(() -> new RuntimeException("The room cannot be found."));

		operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, new ParticipantsCount(room.getParticipantsCount()));
	}

}
