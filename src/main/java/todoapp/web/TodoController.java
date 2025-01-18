package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class TodoController {

    @RequestMapping("/todos")
    public ModelAndView todos() {
        var mav = new ModelAndView();
        mav.setViewName("todos");

        return mav;
    }

}
