package bartender.bartenderback.email.controller;

import bartender.bartenderback.email.dto.EmailAccountResponse;
import bartender.bartenderback.email.dto.EmailAuthRequest;
import bartender.bartenderback.email.dto.EmailSendRequest;
import bartender.bartenderback.email.service.EmailAccountService;
import bartender.bartenderback.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailAccountController {

    private final EmailAccountService emailAccountService;

    @PostMapping("/connect")
    public ApiResponse<EmailAccountResponse> connect(@RequestBody @Valid EmailAuthRequest request) {
        return ApiResponse.ok(emailAccountService.connectAccount(request));
    }

    @GetMapping("/list")
    public ApiResponse<String> getRecentMessages(@RequestParam(defaultValue = "10") int maxResults) {
        return ApiResponse.ok(emailAccountService.listRecentMessages(maxResults));
    }

    @GetMapping("/message/{messageId}")
    public ApiResponse<String> getMessage(@PathVariable String messageId) {
        return ApiResponse.ok(emailAccountService.getMessage(messageId));
    }

    @PostMapping("/send")
    public ApiResponse<Void> sendMail(@RequestBody @Valid EmailSendRequest request) {
        emailAccountService.sendMail(request);
        return ApiResponse.ok(null);
    }
}
