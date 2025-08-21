package bartender.bartenderback.ai.service;

import bartender.bartenderback.ai.domain.AIPrompt;
import bartender.bartenderback.ai.domain.AIPromptRepository;
import bartender.bartenderback.ai.domain.AITaskType;
import bartender.bartenderback.ai.dto.AITaskResponse;
import bartender.bartenderback.ai.dto.AIUserRequest;
import bartender.bartenderback.ai.util.GeminiApiClient;
import bartender.bartenderback.chat.dto.ChatMessageRequest;
import bartender.bartenderback.chat.service.ChatMessageService;
import bartender.bartenderback.email.service.EmailAccountService;
import bartender.bartenderback.task.domain.PriorityType;
import bartender.bartenderback.task.domain.Task;
import bartender.bartenderback.task.domain.TaskRepository;
import bartender.bartenderback.task.dto.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {
    private final GeminiApiClient geminiApiClient;
    private final AIPromptRepository promptRepository;
    private final ChatMessageService chatMessageService;
    private final EmailAccountService emailAccountService;
    private final TaskRepository taskRepository;

    private static final Long FIXED_USER_ID = 1L;

    @Transactional
    public AITaskResponse processUserPrompt(AIUserRequest request) {
        AITaskType taskType = classifyTaskTypeWithGemini(request.getPrompt());

        String aiResult = geminiApiClient.askGemini(request.getPrompt());

        promptRepository.save(new AIPrompt(
                FIXED_USER_ID,
                request.getPrompt(),
                aiResult,
                taskType.name()
        ));

        switch (taskType) {
            case CHAT_SAVE:
                chatMessageService.saveChatMessage(
                        FIXED_USER_ID,
                        new ChatMessageRequest(request.getPrompt(), aiResult)
                );
                break;

            case EMAIL_SUMMARY:
                emailAccountService.saveEmailSummary(request.getOriginId(), aiResult);
                break;

            case EMAIL_SEND:
                validateEmailFields(request);
                emailAccountService.sendMailAuto(
                        request.getTo(),
                        request.getTitle(),
                        request.getBody()
                );
                break;

            case TASK_CREATE:
            case EMAIL_TASK_CREATE:
                createTaskFromAiText(aiResult, FIXED_USER_ID,
                        StringUtils.hasText(request.getOriginType()) ? request.getOriginType() : "UNKNOWN",
                        request.getOriginId()
                );
                break;

            case TASK_UPDATE:
                if (request.getTaskId() == null)
                    throw new IllegalArgumentException("Task ID is required for update.");
                updateTaskFromAiText(request.getTaskId(), aiResult);
                break;

            case TASK_DELETE:
                if (request.getTaskId() == null)
                    throw new IllegalArgumentException("Task ID is required for delete.");
                deleteTaskById(request.getTaskId());
                break;

            case EMAIL_TASK_UPDATE:
                if (request.getTaskId() == null)
                    throw new IllegalArgumentException("Task ID is required for email task update.");
                updateTaskFromAiText(request.getTaskId(), aiResult);
                break;

            case EMAIL_TASK_DELETE:
                if (request.getTaskId() == null)
                    throw new IllegalArgumentException("Task ID is required for email task delete.");
                deleteTaskById(request.getTaskId());
                break;

            default:
                throw new IllegalArgumentException("Unsupported task type: " + taskType);
        }

        return new AITaskResponse(aiResult);
    }

    private void validateEmailFields(AIUserRequest request) {
        if (!StringUtils.hasText(request.getTo())
                || !StringUtils.hasText(request.getTitle())
                || !StringUtils.hasText(request.getBody())) {
            throw new IllegalArgumentException("Required fields for email sending: to, subject, body.");
        }
    }

    @Transactional
    public Task createTaskFromAiText(String aiResult, Long userId, String originType, String originId) {
        TaskRequest taskRequest = TaskRequest.builder()
                .title(parseTitleFromAiResult(aiResult))
                .content(aiResult)
                .priority(PriorityType.MIDDLE)
                .category(null)
                .startDate(null)
                .dueDate()

        taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task updateTaskFromAiText(Long taskId, String aiResult) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        String newTitle = parseTitleFromAiResult(aiResult);
        String newDescription = aiResult;
        LocalDateTime newDueDate = LocalDateTime.now().plusDays(1);
        TaskStatus newStatus = TaskStatus.PENDING;

        task.update(newTitle, newDescription, newDueDate, newStatus);
        return task;
    }

    @Transactional
    public void deleteTaskById(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    private String parseTitleFromAiResult(String aiResult) {
        return aiResult.length() > 50 ? aiResult.substring(0, 50) : aiResult;
    }

    private LocalDate parseDueDateFromAiResult(String aiResult) {
        return LocalDate.now().plusDays(1);
    }

    public List<AIPrompt> getConversationHistory(Long userId) {
        return promptRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    private AITaskType classifyTaskTypeWithGemini(String prompt) {
        String classificationPrompt =
                "아래 사용자의 요청이 EMAIL_SEND, EMAIL_SUMMARY, TASK_CREATE, TASK_UPDATE, TASK_DELETE, EMAIL_TASK_CREATE, EMAIL_TASK_UPDATE, EMAIL_TASK_DELETE, CHAT_SAVE 중 어떤 작업에 해당하는지 한 단어로만 대답해줘(대문자):\n"
                        + prompt;

        String aiTypeString = geminiApiClient.askGemini(classificationPrompt).trim();
        String onlyType = aiTypeString.split("\\s")[0].replaceAll("[^A-Z_]", "");
        try {
            return AITaskType.valueOf(onlyType);
        } catch (Exception e) {
            return AITaskType.CHAT_SAVE;
        }
    }
}
