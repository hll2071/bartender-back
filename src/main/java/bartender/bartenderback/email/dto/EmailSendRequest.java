package bartender.bartenderback.email.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailSendRequest {

    @NotBlank
    private String to;

    @NotBlank
    private String title;

    @NotBlank
    private String body;
}
