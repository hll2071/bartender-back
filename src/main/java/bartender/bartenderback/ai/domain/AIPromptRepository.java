package bartender.bartenderback.ai.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIPromptRepository extends JpaRepository<AIPrompt, Long> {
    List<AIPrompt> findByUserIdOrderByCreatedAtDesc(Long userId);
}
