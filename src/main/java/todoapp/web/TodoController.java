package todoapp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import todoapp.web.model.SiteProperties;

import java.util.Objects;


/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class TodoController {

    private final Environment environment;
    private final String siteAuthor;
    private final SiteProperties siteProperties;

    public TodoController(Environment environment, @Value("${todoapp.site.author}") String siteAuthor, SiteProperties siteProperties) {
        this.environment = Objects.requireNonNull(environment);
        this.siteAuthor = Objects.requireNonNull(siteAuthor);
        this.siteProperties = Objects.requireNonNull(siteProperties);
    }

    @RequestMapping("/todos")
    public ModelAndView todos() {
        // var siteProperties = new SiteProperties();
        // siteProperties.setAuthor("Arawn Park");
        // siteProperties.setAuthor(environment.getProperty("todoapp.site.author"));
        // siteProperties.setAuthor(siteAuthor);

        var mav = new ModelAndView();
        mav.addObject("site", siteProperties);
        mav.setViewName("todos");

        return mav;
    }

}
