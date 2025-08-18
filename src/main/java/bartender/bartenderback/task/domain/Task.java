package bartender.bartenderback.task.domain;

import bartender.bartenderback.task.dto.TaskRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "task")
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private PriorityType priority;

    @Column(nullable = false)
    private String category;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private boolean completed = false;

    public static Task of(TaskRequest taskRequest) {
        return new Task(
                taskRequest.getTitle(),
                taskRequest.getContent(),
                taskRequest.getPriority(),
                taskRequest.getCategory(),
                taskRequest.getStartDate(),
                taskRequest.getDueDate(),
                false
        );
    }

    public void complete() {
        this.completed = true;
    }

    public void update(TaskRequest taskRequest) {
        this.title = taskRequest.getTitle();
        this.content = taskRequest.getContent();
        this.priority = taskRequest.getPriority();
        this.category = taskRequest.getCategory();
        this.startDate = taskRequest.getStartDate();
        this.dueDate = taskRequest.getDueDate();
    }

    Task(String title, String content, PriorityType priority, String category, LocalDateTime startDate, LocalDateTime dueDate, boolean completed) {
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.category = category;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.completed = completed;
    }
}
