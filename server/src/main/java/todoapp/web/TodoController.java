package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.TodoFind;
import todoapp.web.convert.TodoToSpreadsheetConverter;

import java.util.Objects;

@Controller
public class TodoController {

    private final TodoFind find;

    public TodoController(TodoFind find) {
        this.find = Objects.requireNonNull(find);
    }
    
    @RequestMapping("/todos")
    public void todos() {

    }

    @RequestMapping(value = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute(new TodoToSpreadsheetConverter().convert(find.all()));
    }

}
