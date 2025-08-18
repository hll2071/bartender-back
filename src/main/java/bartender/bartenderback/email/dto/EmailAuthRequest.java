package bartender.bartenderback.email.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailAuthRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "accessToken이 필요합니다.")
    private String accessToken;

    @NotBlank(message = "refreshToken이 필요합니다.")
    private String refreshToken;
}
