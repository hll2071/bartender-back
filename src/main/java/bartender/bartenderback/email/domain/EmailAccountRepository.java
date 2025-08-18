package bartender.bartenderback.email.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long> {
    Optional<EmailAccount> findByUserId(Long userId);

}
