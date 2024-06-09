package me.yeon.thread.util;

import static me.yeon.thread.util.AppConstants.*;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.domain.Room;
import me.yeon.thread.repository.ChatRepository;
import me.yeon.thread.repository.RoomRepository;
import me.yeon.thread.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class Initializer implements ApplicationRunner {

	private final RoomRepository roomRepository;
	private final UserRepository userRepository;
	private final ChatRepository chatRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		roomRepository.deleteAll();
		roomRepository.removeAllParticipants();
		userRepository.deleteAll();
		chatRepository.deleteAll();

		roomRepository.save(new Room(DEFAULT_ROOM_CODE, "Welcome! Feel free to chat.", 50, 0));
	}

}
