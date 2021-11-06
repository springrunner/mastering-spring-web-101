package todoapp.web;

import java.util.Objects;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
public class TodoRestController {
    
    private final TodoFinder finder;
    
    public TodoRestController(TodoFinder finder) {
        this.finder = Objects.requireNonNull(finder);
    }
    
    @RequestMapping("/api/todos")
    public Iterable<Todo> todos() {
        return finder.getAll();
    }

}
