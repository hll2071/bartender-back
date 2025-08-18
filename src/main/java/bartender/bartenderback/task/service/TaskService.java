package bartender.bartenderback.task.service;

import bartender.bartenderback.task.domain.Task;
import bartender.bartenderback.task.domain.TaskRepository;
import bartender.bartenderback.task.dto.TaskRequest;
import bartender.bartenderback.task.dto.TaskResponse;
import bartender.bartenderback.task.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.of(request);

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findByOrderByDueDateAsc()
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    public List<TaskResponse> getTasksByCategory(String category) {
        return taskRepository.findByCategoryOrderByDueDateAsc(category)
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    public TaskResponse getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("작업을 찾을 수 없습니다. id : " + id));

        return TaskResponse.from(task);
    }

    public TaskResponse completedTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("작업을 찾을 수 없습니다. id : " + id));

        task.complete();
        Task updatedTask = taskRepository.save(task);

        return TaskResponse.from(updatedTask);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("작업을 찾을 수 없습니다. id : " + id));

        task.update(request);
        Task updatedTask = taskRepository.save(task);

        return TaskResponse.from(updatedTask);
    }
}
