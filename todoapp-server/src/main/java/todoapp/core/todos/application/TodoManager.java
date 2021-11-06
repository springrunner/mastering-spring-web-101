package todoapp.core.todos.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoEntityNotFoundException;
import todoapp.core.todos.domain.TodoRepository;

/**
 * 할 일 검색기({@link TodoFinder}), 편집기(@{@link TodoEditor}) 인터페이스 구현체
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
public class TodoManager implements TodoFinder, TodoEditor {

    private TodoRepository todoRepository;

    public TodoManager(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    @Override
    public void create(String title) {
        todoRepository.save(Todo.create(title));
    }

    @Override
    public void update(Long id, String title, boolean completed) {
        Todo todo = todoRepository.findById(id)
                                  .orElseThrow(() -> new TodoEntityNotFoundException(id));

        todo.update(title, completed);
    }

    @Override
    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                                  .orElseThrow(() -> new TodoEntityNotFoundException(id));

        todoRepository.delete(todo);
    }

}
