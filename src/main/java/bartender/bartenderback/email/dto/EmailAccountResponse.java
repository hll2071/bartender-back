package bartender.bartenderback.email.dto;

import bartender.bartenderback.email.domain.EmailAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EmailAccountResponse {

    private Long id;
    private String email;
    private LocalDateTime lastTokenUpdate;

    public static EmailAccountResponse from(EmailAccount account) {
        return new EmailAccountResponse(
                account.getId(),
                account.getEmail(),
                account.getLastTokenUpdate()
        );
    }
}
