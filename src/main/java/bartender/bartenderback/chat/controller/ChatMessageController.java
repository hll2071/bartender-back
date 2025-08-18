package bartender.bartenderback.chat.controller;

import bartender.bartenderback.chat.dto.ChatMessageRequest;
import bartender.bartenderback.chat.dto.ChatMessageResponse;
import bartender.bartenderback.chat.service.ChatMessageService;
import bartender.bartenderback.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    private static final Long FIXED_USER_ID = 1L;

    @PostMapping("/message")
    public ApiResponse<ChatMessageResponse> saveChatMessage(@RequestBody @Valid ChatMessageRequest request) {
        return ApiResponse.ok(chatMessageService.saveChatMessage(FIXED_USER_ID, request));
    }

    @GetMapping("/history")
    public ApiResponse<List<ChatMessageResponse>> getChatHistory() {
        return ApiResponse.ok(chatMessageService.getChatHistory(FIXED_USER_ID));
    }
}
