package todoapp.core.todo.data;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import todoapp.Constant;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoId;
import todoapp.core.todo.domain.TodoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 메모리 기반 할일 저장소 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Profile(Constant.PROFILE_DEVELOPMENT)
@Repository
class InMemoryTodoRepository implements TodoRepository {

    private final List<Todo> todos = new CopyOnWriteArrayList<>();

    @Override
    public List<Todo> findAll() {
        return Collections.unmodifiableList(todos);
    }

    @Override
    public List<Todo> findByUsername(String username) {
        var result = todos.stream().filter(todo -> Objects.equals(username, todo.getUsername())).toList();
        return Collections.unmodifiableList(result);
    }

    public Optional<Todo> findById(TodoId id) {
        return todos.stream().filter(todo -> Objects.equals(id, todo.getId())).findFirst();
    }

    @Override
    public Todo save(Todo todo) {
        if (!todos.contains(todo)) {
            todos.add(todo);
        }
        return todo;
    }

    @Override
    public void delete(Todo todo) {
        todos.remove(todo);
    }

}
