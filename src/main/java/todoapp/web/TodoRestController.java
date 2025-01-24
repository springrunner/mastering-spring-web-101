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

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RolesAllowed(UserSession.ROLE_USER)
@RestController
@RequestMapping(path = "/api/todos")
public class TodoRestController {

    private final FindTodos find;
    private final AddTodo add;
    private final ModifyTodo modify;
    private final RemoveTodo remove;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public TodoRestController(FindTodos find, AddTodo add, ModifyTodo modify, RemoveTodo remove) {
        this.find = Objects.requireNonNull(find);
        this.add = Objects.requireNonNull(add);
        this.modify = Objects.requireNonNull(modify);
        this.remove = Objects.requireNonNull(remove);
    }

    @GetMapping
    public Iterable<Todo> readAll() {
        return find.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid WriteTodoCommand command) {
        log.debug("request command: {}", command);

        add.add(command.text());
    }

    @PutMapping("/{id}")
    public void modify(@PathVariable("id") String id, @RequestBody @Valid WriteTodoCommand command) {
        log.debug("request id: {}, command: {}", id, command);

        modify.modify(TodoId.of(id), command.text, command.completed());
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") String id) {
        log.debug("request id: {}", id);

        remove.remove(TodoId.of(id));
    }

    record WriteTodoCommand(@NotBlank @Size(min = 4, max = 140) String text, boolean completed) {
    }

}
