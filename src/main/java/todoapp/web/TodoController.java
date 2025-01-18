package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.domain.support.SpreadsheetConverter;
import todoapp.web.model.SiteProperties;

import java.util.Objects;


/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class TodoController {

    private final SiteProperties siteProperties;
    private final FindTodos find;

    public TodoController(SiteProperties siteProperties, FindTodos find) {
        this.siteProperties = Objects.requireNonNull(siteProperties);
        this.find = Objects.requireNonNull(find);
    }

    @RequestMapping("/todos")
    public void todos(Model model) {
        model.addAttribute("site", siteProperties);
    }

    @RequestMapping(value = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute(SpreadsheetConverter.convert(find.all()));
    }

}
