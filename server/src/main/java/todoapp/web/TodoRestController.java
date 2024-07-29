package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todo.application.TodoCleanup;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.application.TodoModification;
import todoapp.core.todo.application.TodoRegistry;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoId;
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

    private final TodoFind find;
    private final TodoRegistry registry;
    private final TodoModification modification;
    private final TodoCleanup cleanup;

    public TodoRestController(TodoFind find, TodoRegistry registry, TodoModification modification, TodoCleanup cleanup) {
        this.find = Objects.requireNonNull(find);
        this.registry = Objects.requireNonNull(registry);
        this.modification = Objects.requireNonNull(modification);
        this.cleanup = Objects.requireNonNull(cleanup);
    }

    @GetMapping
    public List<Todo> readAll() {
        return find.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid WriteTodoCommand command) {
        log.debug("request command: {}", command);

        registry.register(command.text());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id, @RequestBody @Valid WriteTodoCommand command) {
        log.debug("request id: {}, command: {}", id, command);

        modification.modify(TodoId.of(id), command.text(), command.completed());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.debug("request id: {}", id);

        cleanup.clear(TodoId.of(id));
    }

    record WriteTodoCommand(@NotBlank @Size(min = 4, max = 140) String text, boolean completed) {

    }

}
