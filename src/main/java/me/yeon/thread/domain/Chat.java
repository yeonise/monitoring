package me.yeon.thread.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Chat {

	private final String content;
	private final String createdAt;
	private final String sender;

}
