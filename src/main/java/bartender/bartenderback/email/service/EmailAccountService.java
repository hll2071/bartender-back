package bartender.bartenderback.email.service;

import bartender.bartenderback.email.domain.EmailAccount;
import bartender.bartenderback.email.domain.EmailAccountRepository;
import bartender.bartenderback.email.dto.EmailAccountResponse;
import bartender.bartenderback.email.dto.EmailAuthRequest;
import bartender.bartenderback.email.dto.EmailSendRequest;
import bartender.bartenderback.email.util.GmailApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailAccountService {
    private final EmailAccountRepository accountRepository;
    private final GmailApiClient gmailApiClient;

    private static final Long FIXED_USER_ID = 1L;

    @Transactional
    public EmailAccountResponse connectAccount(EmailAuthRequest request) {
        EmailAccount emailAccount = accountRepository.findByUserId(FIXED_USER_ID)
                .map(acc -> {
                    acc.updateToken(request.getAccessToken(), request.getRefreshToken());
                    return acc;
                })
                .orElse(new EmailAccount(
                        request.getEmail(),
                        request.getAccessToken(),
                        request.getRefreshToken(),
                        FIXED_USER_ID
                ));
        EmailAccount savedAccount = accountRepository.save(emailAccount);
        return EmailAccountResponse.from(savedAccount);
    }

    public EmailAccount getCurrentAccount() {
        return accountRepository.findByUserId(FIXED_USER_ID)
                .orElseThrow(() -> new IllegalStateException("이메일 연동이 필요합니다."));
    }

    public String listRecentMessages(int maxResults) {
        EmailAccount acc = getCurrentAccount();
        return gmailApiClient.listMessages(acc.getAccessToken(), maxResults);
    }

    public String getMessage(String messageId) {
        EmailAccount acc = getCurrentAccount();
        return gmailApiClient.getMessage(acc.getAccessToken(), messageId);
    }

    public void sendMail(EmailSendRequest request) {
        EmailAccount acc = getCurrentAccount();
        gmailApiClient.sendMail(acc.getAccessToken(), request.getTo(), request.getTitle(), request.getBody());
    }
}
