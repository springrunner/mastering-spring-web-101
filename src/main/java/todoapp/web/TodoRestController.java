package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.application.TodoRegistry;
import todoapp.core.todo.domain.Todo;

import java.util.Objects;

@RestController
@RequestMapping("/api/todos")
public class TodoRestController {

    private final TodoFind find;
    private final TodoRegistry registry;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public TodoRestController(TodoFind find, TodoRegistry registry) {
        this.find = Objects.requireNonNull(find);
        this.registry = Objects.requireNonNull(registry);
    }

    @GetMapping
    public Iterable<Todo> readAll() {
        return find.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateTodoCommand command) {
        log.debug("request command: {}", command);

        registry.register(command.text());
    }

    record CreateTodoCommand(String text) {
    }

}
