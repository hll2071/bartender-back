package bartender.bartenderback.ai.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ai_prompt")
public class AIPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String aiResult;

    @Column(nullable = false)
    private String sourceType; // 작업 타입(EX: CHAT_SAVE, EMAIL_SEND)

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public AIPrompt(Long userId, String prompt, String aiResult, String sourceType) {
        this.userId = userId;
        this.prompt = prompt;
        this.aiResult = aiResult;
        this.sourceType = sourceType;
        this.createdAt = LocalDateTime.now();
    }
}
