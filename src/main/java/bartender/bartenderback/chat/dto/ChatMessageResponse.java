package bartender.bartenderback.chat.dto;

import bartender.bartenderback.chat.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatMessageResponse {
    private Long id;
    private Long userId;
    private String userMessage;
    private String aiMessage;
    private LocalDateTime createdAt;

    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getId(),
                chatMessage.getUserId(),
                chatMessage.getUserMessage(),
                chatMessage.getAiMessage(),
                chatMessage.getCreatedAt()
        );
    }
}
