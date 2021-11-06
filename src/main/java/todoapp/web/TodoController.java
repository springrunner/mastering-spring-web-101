package todoapp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {
       
    private final Environment environment;
    private final String siteAuthor;
    private final SiteProperties siteProperties;
    
    public TodoController(Environment environment, @Value("${site.author}") String siteAuthor, SiteProperties siteProperties) {
        this.environment = environment;
        this.siteAuthor = siteAuthor;
        this.siteProperties = siteProperties;
    }
    
    @ModelAttribute("site")
    public SiteProperties siteProperties() {
        return siteProperties;
    }
    
    @RequestMapping("/todos")
    public void todos() {
        
    }
    
}
