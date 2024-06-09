package me.yeon.thread.util.interceptor;

import static me.yeon.thread.util.AppConstants.*;

import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.yeon.thread.config.CookieProperties;
import me.yeon.thread.domain.User;
import me.yeon.thread.repository.UserRepository;
import me.yeon.thread.util.RandomGenerator;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

	private final CookieProperties cookieProperties;
	private final UserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (CorsUtils.isCorsRequest(request)) return true;

		Stream<Cookie> cookieStream = request.getCookies() == null ? Stream.empty() : Stream.of(request.getCookies());
		String sessionCode = cookieStream
			.filter(cookie -> cookie.getName().equals(cookieProperties.getName()))
			.map(Cookie::getValue)
			.filter(userRepository::existsById)
			.findAny()
			.orElseGet(() -> generateSession(request, response));

		request.setAttribute(SESSION_ATTRIBUTE_KEY, sessionCode);
		response.setHeader(HttpHeaders.SET_COOKIE, generateCookie(sessionCode));

		return true;
	}

	private String generateSession(HttpServletRequest request, HttpServletResponse response) {
		String sessionCode = RandomGenerator.generateCode(10);
		String nickname = RandomGenerator.generateNickname();

		User user = new User(sessionCode, nickname);
		userRepository.save(user);

		return sessionCode;
	}

	private String generateCookie(String sessionCode) {
		return ResponseCookie.from(cookieProperties.getName(), sessionCode)
			.domain(cookieProperties.getDomain())
			.path(cookieProperties.getPath())
			.sameSite(cookieProperties.getSameSite())
			.maxAge(cookieProperties.getExpiry())
			.httpOnly(true)
			.secure(true)
			.build()
			.toString();
	}

}
