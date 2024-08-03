package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.domain.Todo;

import java.util.Objects;

@Controller
public class TodoRestController {

    private final TodoFind find;

    public TodoRestController(TodoFind find) {
        this.find = Objects.requireNonNull(find);
    }

    @RequestMapping("/api/todos")
    public @ResponseBody Iterable<Todo> readAll() {
        return find.all();
    }

}
