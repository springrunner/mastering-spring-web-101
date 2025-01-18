package todoapp.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.domain.Todo;
import todoapp.web.model.SiteProperties;

import java.util.*;


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
        model.addAttribute("todos", find.all());
    }

    public static class TodoCsvViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            if ("todos".equals(viewName)) {
                return new TodoCsvView();
            }
            return null;
        }
    }

    public static class TodoCsvView extends AbstractView implements View {

        private final Logger log = LoggerFactory.getLogger(TodoController.class);

        public TodoCsvView() {
            setContentType("text/csv");
        }

        @Override
        protected boolean generatesDownloadContent() {
            return true;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            log.info("render model as csv content");

            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todos.csv\"");
            response.getWriter().println("id,text,completed");

            var todos = (List<Todo>) model.getOrDefault("todos", Collections.emptyList());
            for (var todo : todos) {
                var line = "%s,%s,%s".formatted(todo.getId().toString(), todo.getText(), todo.isCompleted());
                response.getWriter().println(line);
            }

            response.flushBuffer();
        }
    }

}
