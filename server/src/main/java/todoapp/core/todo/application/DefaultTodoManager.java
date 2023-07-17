package todoapp.core.todo.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.core.todo.domain.*;

import java.util.List;
import java.util.Objects;

/**
 * 할일 목록을 위한 유스케이스 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
class DefaultTodoManager implements TodoFind, TodoRegistry, TodoModification, TodoCleanup {

    private final TodoIdGenerator todoIdGenerator;
    private final TodoRepository todoRepository;

    public DefaultTodoManager(TodoIdGenerator todoIdGenerator, TodoRepository todoRepository) {
        this.todoIdGenerator = Objects.requireNonNull(todoIdGenerator);
        this.todoRepository = Objects.requireNonNull(todoRepository);
    }

    @Override
    public List<Todo> all() {
        return todoRepository.findAll();
    }

    @Override
    public Todo byId(TodoId id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoEntityNotFoundException(id));
    }

    @Override
    public TodoId register(String text) {
        return todoRepository.save(Todo.create(text, todoIdGenerator)).getId();
    }

    @Override
    public void modify(TodoId id, String text, boolean completed) {
        byId(id).update(text, completed);
    }

    @Override
    public void clear(TodoId id) {
        todoRepository.delete(byId(id));
    }

}
