package me.yeon.thread.util;

import static me.yeon.thread.util.AppConstants.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.dto.ThreadStatus;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class MetricsFetcher {

	private final RestTemplate restTemplate = new RestTemplate();
	private final SimpMessageSendingOperations operations;

	@Scheduled(fixedRate = 1800000, initialDelay = 10000) // Update every 5 seconds
	public void sendActiveExecutors() throws JsonProcessingException {
		String url = "http://localhost:9000/monitor/metrics/executor.active";
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

		String responseBody = responseEntity.getBody();
		assert responseBody != null;

		List<String> activeExecutors = extractActiveExecutors(responseBody);
		String content = "[ Active Executors List ]"
			+ System.lineSeparator() + String.join(System.lineSeparator(), activeExecutors)
			+ System.lineSeparator() + "at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

		operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, new ThreadStatus(content));
	}

	private static List<String> extractActiveExecutors(String responseBody) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(responseBody);

		List<String> valuesList = new ArrayList<>();
		JsonNode valuesNode = root.path("availableTags").get(0).path("values");

		for (JsonNode valueNode : valuesNode) {
			String value = valueNode.asText();
			value = value.replaceAll("^\"|\"$", "");
			valuesList.add("• " + value);
		}

		return valuesList;
	}

	@Scheduled(fixedRate = 60000, initialDelay = 10000) // Update every 5 seconds
	public void sendDatabaseIOInvocations() throws JsonProcessingException {
		String url = "http://localhost:9000/monitor/metrics/spring.data.repository.invocations";
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

		String responseBody = responseEntity.getBody();
		assert responseBody != null;

		String content = extractInvocations(responseBody);

		operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, new ThreadStatus(content));
	}

	private static String extractInvocations(String responseBody) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(responseBody);

		return "Repository 호출 수: " + root.path("measurements").get(0).get("value").asText()
			+ System.lineSeparator()
			+ "모든 Repository 호출의 총 소요 시간: " + root.path("measurements").get(1).get("value").asText()
			+ System.lineSeparator()
			+ "가장 오랜 시간이 걸린 Repository 호출의 소요 시간: " + root.path("measurements").get(2).get("value").asText()
			+ System.lineSeparator()
			+ "at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
	}

}

