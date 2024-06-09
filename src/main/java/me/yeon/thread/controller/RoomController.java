package me.yeon.thread.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.application.RoomService;
import me.yeon.thread.util.resolver.SessionCode;

@RestController
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;

	@PostMapping("/rooms")
	public ResponseEntity<Integer> enter(@SessionCode String sessionCode) {
		return ResponseEntity.ok(roomService.enter(sessionCode));
	}

}
