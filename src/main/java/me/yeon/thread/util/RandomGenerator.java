package me.yeon.thread.util;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomGenerator {

	private static final AtomicLong nickname = new AtomicLong();

	/**
	 * generate random code
	 * using a-z A-Z 0-9
	 */
	public static String generateCode(int length) {
		String randomString = UUID.randomUUID().toString().replaceAll("-", "");

		return randomString.substring(0, length);
	}

	public static String generateNickname() {
		List<String> adjectives = Arrays.asList("Brave", "Clever", "Joyful", "Calm", "Gentle", "Happy", "Cute", "Beautiful");
		List<String> nouns = Arrays.asList("Lion", "Panda", "Eagle", "Tiger", "Shark", "Cat", "Rabbit");

		int adjectivesIndex = ThreadLocalRandom.current().nextInt(adjectives.size());
		int nounsIndex = ThreadLocalRandom.current().nextInt(nouns.size());

		return adjectives.get(adjectivesIndex) + nouns.get(nounsIndex) + nickname.incrementAndGet();
	}

}
