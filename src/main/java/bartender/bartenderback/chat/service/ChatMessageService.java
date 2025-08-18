package bartender.bartenderback.chat.service;

import bartender.bartenderback.chat.domain.ChatMessage;
import bartender.bartenderback.chat.domain.ChatMessageRepository;
import bartender.bartenderback.chat.dto.ChatMessageRequest;
import bartender.bartenderback.chat.dto.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageResponse saveChatMessage(Long userId, ChatMessageRequest request) {
        ChatMessage chatMessage = new ChatMessage(userId, request);

        ChatMessage saved = chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.from(saved);
    }

    public List<ChatMessageResponse> getChatHistory(Long userId) {
        return chatMessageRepository.findByUserIdOrderByCreatedAtAsc(userId)
                .stream()
                .map(ChatMessageResponse::from)
                .toList();
    }
}
