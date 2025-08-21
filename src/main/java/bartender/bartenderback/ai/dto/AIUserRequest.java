package bartender.bartenderback.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AIUserRequest {

    @NotBlank(message = "프롬프트는 필수값입니다.")
    private String prompt;

    private Long taskId;

    private String to;
    private String title;
    private String body;

    private String originType;
    private String originId;
}
