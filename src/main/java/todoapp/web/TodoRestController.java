package todoapp.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todo.application.AddTodo;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.domain.Todo;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RestController
@RequestMapping(path = "/api/todos")
public class TodoRestController {

    private final FindTodos find;
    private final AddTodo add;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public TodoRestController(FindTodos find, AddTodo add) {
        this.find = Objects.requireNonNull(find);
        this.add = Objects.requireNonNull(add);
    }

    @GetMapping
    public Iterable<Todo> readAll() {
        return find.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid AddTodoCommand command) {
        log.debug("request command: {}", command);

        add.add(command.text());
    }

    record AddTodoCommand(@NotBlank @Size(min = 4, max = 140) String text) {
    }

}
