package bartender.bartenderback.email.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "email_account")
public class EmailAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime lastTokenUpdate;

    @Column(nullable = false)
    private Long userId;

    public EmailAccount(String email, String accessToken, String refreshToken, Long userId) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.lastTokenUpdate = LocalDateTime.now();
        this.userId = userId;

    }

    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.lastTokenUpdate = LocalDateTime.now();
    }
}
