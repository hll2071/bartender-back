package bartender.bartenderback.task.dto;

import bartender.bartenderback.task.domain.PriorityType;
import bartender.bartenderback.task.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String content;
    private PriorityType priority;
    private String category;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean completed;

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getContent(),
                task.getPriority(),
                task.getCategory(),
                task.getStartDate(),
                task.getDueDate(),
                task.isCompleted()
        );
    }
}
