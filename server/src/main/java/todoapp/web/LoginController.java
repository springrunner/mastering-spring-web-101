package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public void loginForm() {

    }

}
