package bartender.bartenderback.email.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class GmailApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public String listMessages(String accessToken, int maxResults) {
        String url = "https://gmail.googleapis.com/gmail/v1/users/me/messages?maxResults=" + maxResults;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getMessage(String accessToken, String messageId) {
        String url = "https://gmail.googleapis.com/gmail/v1/users/me/messages/" + messageId + "?format=full";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public void sendMail(String accessToken, String to, String subject, String body) {
        String rawContent = buildRawEmail(to, subject, body);

        String encoded = Base64.getUrlEncoder().encodeToString(rawContent.getBytes());

        String url = "https://gmail.googleapis.com/gmail/v1/users/me/messages/send";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"raw\": \"%s\"}", encoded);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        restTemplate.postForEntity(url, entity, String.class);
    }

    private String buildRawEmail(String to, String subject, String body) {
        return "To: " + to + "\r\n" +
               "Subject: " + subject + "\r\n" +
               "Content-Type: text/plain; charset=UTF-8\r\n" +
               "\r\n" +
               body;
    }
}
