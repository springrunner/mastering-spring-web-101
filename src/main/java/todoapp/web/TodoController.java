package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.web.model.SiteProperties;

import java.util.Objects;


/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class TodoController {

    private final SiteProperties siteProperties;

    public TodoController(SiteProperties siteProperties) {
        this.siteProperties = Objects.requireNonNull(siteProperties);
    }

    @RequestMapping("/todos")
    public void todos(Model model) {
        model.addAttribute("site", siteProperties);
    }

}
