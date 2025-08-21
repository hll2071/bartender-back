package bartender.bartenderback.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageRequest {

    @NotBlank(message = "사용자 메시지를 입력해주세요.")
    private String userMessage;

    @NotBlank(message = "AI 답변은 필수입니다.")
    private String aiMessage;
}
