package bartender.bartenderback.task.dto;

import bartender.bartenderback.task.domain.PriorityType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TaskRequest {

    @NotBlank(message = "할 일 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "할 일 내용은 필수입니다.")
    private String content;

    @NotBlank(message = "우선순위는 필수입니다.")
    private PriorityType priority;

    private List<CategoryDto> category;

    private LocalDate startDate;

    private LocalDate dueDate;
}
