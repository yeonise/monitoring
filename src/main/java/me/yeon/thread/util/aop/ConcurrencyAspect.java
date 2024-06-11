package me.yeon.thread.util.aop;

import static me.yeon.thread.util.AppConstants.*;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Aspect
@RequiredArgsConstructor
public class ConcurrencyAspect {

	private final RedissonClient redissonClient;

	@Around("@annotation(RoomSynchronized)")
	public Object handleRoomConcurrency(ProceedingJoinPoint joinPoint) {
		RLock lock = redissonClient.getLock(DEFAULT_ROOM_CODE);

		try {
			boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
			if (!available) {
				throw new RuntimeException("");
			}
			return joinPoint.proceed();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		} finally {
			lock.unlock();
		}
	}

}
