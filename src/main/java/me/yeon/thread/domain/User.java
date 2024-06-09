package me.yeon.thread.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RedisHash
@AllArgsConstructor
public class User {

	@Id
	private final String sessionCode;

	@Getter
	private final String nickname;

}
