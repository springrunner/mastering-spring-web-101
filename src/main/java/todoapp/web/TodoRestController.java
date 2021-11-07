package todoapp.web;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
@RequestMapping("/api/todos")
@RolesAllowed("ROLE_USER")
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
    public void create(@RequestBody @Valid WriteTodoCommand command) {
        log.debug("create todo, command: {}", command);
        
        editor.create(command.getTitle());
    }
    
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid WriteTodoCommand command) {
        log.debug("update todo, id: {}, command: {}", id, command);
        
        editor.update(id, command.getTitle(), command.isCompleted());
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.debug("delete todo, id: {}", id);
        
        editor.delete(id);
    }

    static class WriteTodoCommand {
        @NotBlank
        @Size(min = 4, max = 140)
        private String title;
        private boolean completed;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return String.format("[title=%s, completed=%s]", title, completed);
        }
    }
    
}
