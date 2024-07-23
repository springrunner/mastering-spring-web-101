package todoapp.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.application.TodoRegistry;
import todoapp.core.todo.domain.Todo;

import java.util.List;
import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RestController
@RequestMapping("/api/todos")
public class TodoRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TodoFind find;
    private final TodoRegistry registry;

    public TodoRestController(TodoFind find, TodoRegistry registry) {
        this.find = Objects.requireNonNull(find);
        this.registry = Objects.requireNonNull(registry);
    }

    @GetMapping
    public List<Todo> readAll() {
        return find.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CreateTodoCommand command) {
        log.debug("request command: {}", command);

        registry.register(command.text());
    }

    record CreateTodoCommand(@NotBlank @Size(min = 4, max = 140) String text) {

    }

}
