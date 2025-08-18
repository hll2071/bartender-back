package bartender.bartenderback.task.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOrderByDueDateAsc();

    List<Task> findByCategoryOrderByDueDateAsc(String category);
}
