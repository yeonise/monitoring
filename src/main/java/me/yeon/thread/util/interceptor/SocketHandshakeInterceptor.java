package me.yeon.thread.util.interceptor;

import static me.yeon.thread.util.AppConstants.*;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.yeon.thread.config.CookieProperties;

@Component
@RequiredArgsConstructor
public class SocketHandshakeInterceptor implements HandshakeInterceptor {

	private final CookieProperties cookieProperties;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws
		Exception {
		ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest)request;
		HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();
		Stream<Cookie> cookieStream = httpServletRequest.getCookies() == null ? Stream.empty() : Stream.of(httpServletRequest.getCookies());

		String sessionCode = cookieStream
			.filter(cookie -> cookie.getName().equals(cookieProperties.getName()))
			.map(Cookie::getValue)
			.findAny()
			.orElseThrow(() -> new RuntimeException("Cookie does not exist."));

		attributes.put(SESSION_ATTRIBUTE_KEY, sessionCode);

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	}

}
