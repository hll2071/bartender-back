package bartender.bartenderback.task.controller;

import bartender.bartenderback.global.dto.ApiResponse;
import bartender.bartenderback.task.dto.TaskRequest;
import bartender.bartenderback.task.dto.TaskResponse;
import bartender.bartenderback.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ApiResponse<TaskResponse> createTask(@RequestBody @Valid TaskRequest request) {
        return ApiResponse.ok(taskService.createTask(request));
    }

    @GetMapping
    public ApiResponse<List<TaskResponse>> getAllTasks() {
        return ApiResponse.ok(taskService.getAllTasks());
    }

    @GetMapping("/category/{category}")
    public ApiResponse<List<TaskResponse>> getTasksByCategory(@PathVariable String category) {
        return ApiResponse.ok(taskService.getTasksByCategory(category));
    }

    @GetMapping("/{id}")
    public ApiResponse<TaskResponse> getTask(@PathVariable Long id) {
        return ApiResponse.ok(taskService.getTask(id));
    }

    @PostMapping("/{id}/complete")
    public ApiResponse<TaskResponse> completeTask(@PathVariable Long id) {
        return ApiResponse.ok(taskService.completedTask(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskResponse> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest request) {
        return ApiResponse.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<TaskResponse> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ApiResponse.ok(null);
    }
}
