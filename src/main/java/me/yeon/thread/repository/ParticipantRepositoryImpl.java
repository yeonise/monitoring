package me.yeon.thread.repository;

import static me.yeon.thread.util.AppConstants.*;

import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParticipantRepositoryImpl implements ParticipantRepository {

	private final StringRedisTemplate stringRedisTemplate;

	@Override
	public void addParticipant(String sessionCode) {
		stringRedisTemplate.opsForSet().add(DEFAULT_PARTICIPANT_SET_KEY, sessionCode);
	}

	@Override
	public void removeParticipant(String sessionCode) {
		stringRedisTemplate.opsForSet().remove(DEFAULT_PARTICIPANT_SET_KEY, sessionCode);
	}

	@Override
	public void removeAllParticipants() {
		stringRedisTemplate.delete(DEFAULT_PARTICIPANT_SET_KEY);
	}

	@Override
	public Boolean isParticipant(String sessionCode) {
		return stringRedisTemplate.opsForSet().isMember(DEFAULT_PARTICIPANT_SET_KEY, sessionCode);
	}

}
