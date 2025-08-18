package bartender.bartenderback.task.dto;

import bartender.bartenderback.task.domain.PriorityType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class TaskRequest {

    @NotBlank(message = "할 일 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "할 일 내용은 필수입니다.")
    private String content;

    @NotBlank(message = "우선순위는 필수입니다.")
    private PriorityType priority;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;
}
