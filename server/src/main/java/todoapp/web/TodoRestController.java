package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.shared.identifier.TodoId;
import todoapp.core.todo.application.AddTodo;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.application.ModifyTodo;
import todoapp.core.todo.application.RemoveTodo;
import todoapp.core.todo.domain.Todo;
import todoapp.security.UserSession;

import java.util.List;
import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RolesAllowed(UserSession.ROLE_USER)
@RestController
@RequestMapping("/api/todos")
public class TodoRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final FindTodos findTodos;
    private final AddTodo addTodo;
    private final ModifyTodo modifyTodo;
    private final RemoveTodo removeTodo;

    public TodoRestController(FindTodos findTodos, AddTodo addTodo, ModifyTodo modifyTodo, RemoveTodo removeTodo) {
        this.findTodos = Objects.requireNonNull(findTodos);
        this.addTodo = Objects.requireNonNull(addTodo);
        this.modifyTodo = Objects.requireNonNull(modifyTodo);
        this.removeTodo = Objects.requireNonNull(removeTodo);
    }

    @GetMapping
    public List<Todo> readAll() {
        return findTodos.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid WriteTodoCommand command) {
        log.debug("request command: {}", command);

        addTodo.add(command.text());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id, @RequestBody @Valid WriteTodoCommand command) {
        log.debug("request id: {}, command: {}", id, command);

        modifyTodo.modify(TodoId.of(id), command.text(), command.completed());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        log.debug("request id: {}", id);

        removeTodo.remove(TodoId.of(id));
    }

    record WriteTodoCommand(@NotBlank @Size(min = 4, max = 140) String text, boolean completed) {

    }

}
