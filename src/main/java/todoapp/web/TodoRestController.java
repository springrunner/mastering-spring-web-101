package todoapp.web;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
@RequestMapping("/api/todos")
public class TodoRestController {
    
    private final TodoFinder finder;
    private final TodoEditor editor;
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public TodoRestController(TodoFinder finder, TodoEditor editor) {
        this.finder = Objects.requireNonNull(finder);
        this.editor = Objects.requireNonNull(editor);
    }
    
    @GetMapping
    public Iterable<Todo> todos() {
        return finder.getAll();
    }
    
    @PostMapping
    public void create(@RequestBody @Valid CreateTodoCommand command) {
        log.debug("request command: {}", command);
        
        editor.create(command.getTitle());
    }

    static class CreateTodoCommand {
        @NotBlank
        @Size(min = 4, max = 140)
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return String.format("[title=%s]", title);
        }
    }
    
}
