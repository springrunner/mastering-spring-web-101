package todoapp.core.todo.application;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoEntityNotFoundException;
import todoapp.core.todo.domain.TodoRepository;

/**
 * 할일 관리를 위한 유스케이스 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
class DefaultTodoManager implements TodoFind, TodoRegistry, TodoModification, TodoCleanup {

  private final TodoRepository todoRepository;

  public DefaultTodoManager(TodoRepository todoRepository) {
    this.todoRepository = Objects.requireNonNull(todoRepository);
  }

  @Override
  public List<Todo> all() {
    return todoRepository.findAll();
  }

  @Override
  public Todo byId(Long id) {
    return todoRepository.findById(id).orElseThrow(() -> new TodoEntityNotFoundException(id));
  }

  @Override
  public void register(String title) {
    todoRepository.save(Todo.create(title));
  }

  @Override
  public void modify(Long id, String title, boolean completed) {
    byId(id).update(title, completed);
  }

  @Override
  public void clear(Long id) {
    todoRepository.delete(byId(id));
  }

}
