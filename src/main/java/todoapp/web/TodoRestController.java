package todoapp.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.domain.Todo;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RestController
public class TodoRestController {

    private final FindTodos find;

    public TodoRestController(FindTodos find) {
        this.find = Objects.requireNonNull(find);
    }

    @RequestMapping(path = "/api/todos")
    public Iterable<Todo> readAll() {
        return find.all();
    }

}
