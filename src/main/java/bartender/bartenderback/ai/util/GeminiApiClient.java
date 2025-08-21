package bartender.bartenderback.ai.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiApiClient {

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();

    public String askGemini(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-goog-api-key", apiKey);

            String requestBody = String.format(
                    "{ \"contents\": [ { \"parts\": [ { \"text\": \"%s\" } ] } ] }",
                    escapeJson(prompt)
            );

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
            return extractResultFromJson(response.getBody());
        } catch(Exception e) {
            throw new RuntimeException("Gemini API 호출을 실패 : " + e.getMessage(), e);
        }
    }

    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String extractResultFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && !candidates.isEmpty()) {
                JsonNode first = candidates.get(0);
                JsonNode content = first.path("content");
                JsonNode parts = content.path("parts");
                if (parts.isArray() && !parts.isEmpty()) {
                    JsonNode part = parts.get(0);
                    if (part.has("text")) {
                        return part.get("text").asText();
                    }
                }
            }
            return json;
        } catch (Exception e) {
            throw new RuntimeException("Gemini 응답 JSON 파싱 실패: " + e.getMessage(), e);
        }
    }
}
