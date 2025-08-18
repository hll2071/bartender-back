package bartender.bartenderback.task.domain;

import bartender.bartenderback.task.dto.Category;
import bartender.bartenderback.task.dto.CategoryDto;
import bartender.bartenderback.task.dto.TaskRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_categories", joinColumns = @JoinColumn(name = "task_id"))
    private List<Category> category;

    private LocalDate startDate;

    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean completed = false;

    public static Task of(TaskRequest taskRequest) {
        Task task =  new Task(
                taskRequest.getTitle(),
                taskRequest.getContent(),
                taskRequest.getPriority(),
                new ArrayList(),
                taskRequest.getStartDate(),
                taskRequest.getDueDate(),
                false
        );
        if(taskRequest.getCategory() != null) {
            task.setCategoriesFromDto(taskRequest.getCategory());
        }

        return task;
    }

    public void complete() {
        this.completed = true;
    }

    public void update(TaskRequest taskRequest) {
        this.title = taskRequest.getTitle();
        this.content = taskRequest.getContent();
        this.priority = taskRequest.getPriority();
        this.startDate = taskRequest.getStartDate();
        this.dueDate = taskRequest.getDueDate();
        if (taskRequest.getCategory() != null) {
            setCategoriesFromDto(taskRequest.getCategory());
        }
    }

    Task(String title, String content, PriorityType priority, List<Category> category, LocalDate startDate, LocalDate dueDate, boolean completed) {
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.category = category;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    private void setCategoriesFromDto(List<CategoryDto> dtos) {
        this.category.clear();
        for (CategoryDto dto : dtos) {
            this.category.add(new Category(dto.getContent(), dto.getColor()));
        }
    }
}
