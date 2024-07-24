package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.TodoFind;
import todoapp.web.convert.TodoToSpreadsheetConverter;
import todoapp.web.model.SiteProperties;

import java.util.Objects;

@Controller
public class TodoController {

    private final TodoFind find;
    private final SiteProperties siteProperties;

    public TodoController(TodoFind find, SiteProperties siteProperties) {
        this.find = Objects.requireNonNull(find);
        this.siteProperties = Objects.requireNonNull(siteProperties);
    }

    @ModelAttribute("site")
    public SiteProperties siteProperties() {
        return siteProperties;
    }

    @RequestMapping("/todos")
    public void todos() {

    }

    @RequestMapping(value = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute(new TodoToSpreadsheetConverter().convert(find.all()));
    }
    
}
