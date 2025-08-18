package bartender.bartenderback.chat.domain;

import bartender.bartenderback.chat.dto.ChatMessageRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String aiMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ChatMessage(Long userId, ChatMessageRequest request) {
        this.userId = userId;
        this.userMessage = request.getUserMessage();
        this.aiMessage = request.getAiMessage();
        this.createdAt = LocalDateTime.now();
    }
}
