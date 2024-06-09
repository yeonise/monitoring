package me.yeon.thread.repository;

import static me.yeon.thread.util.AppConstants.*;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.domain.Chat;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

	private final RedisTemplate<String, Chat> chatRedisTemplate;

	public List<Chat> findAll() {
		return chatRedisTemplate.opsForList().range(DEFAULT_CHAT_LIST_KEY, 0, -1);
	}

	public void save(Chat chat) {
		chatRedisTemplate.opsForList().rightPush(DEFAULT_CHAT_LIST_KEY, chat);
		chatRedisTemplate.opsForList().trim(DEFAULT_CHAT_LIST_KEY, -200, -1);
	}

	public void deleteAll() {
		chatRedisTemplate.delete(DEFAULT_CHAT_LIST_KEY);
	}

}
