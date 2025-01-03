package todoapp.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import todoapp.core.user.application.RegisterUser;
import todoapp.core.user.application.VerifyUserPassword;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class LoginController {

    private final VerifyUserPassword verifyUserPassword;
    private final RegisterUser registerUser;
    private final UserSessionHolder userSessionHolder;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public LoginController(VerifyUserPassword verifyUserPassword, RegisterUser registerUser, UserSessionHolder userSessionHolder) {
        this.verifyUserPassword = Objects.requireNonNull(verifyUserPassword);
        this.registerUser = Objects.requireNonNull(registerUser);
        this.userSessionHolder = Objects.requireNonNull(userSessionHolder);
    }

    @GetMapping("/login")
    public String loginForm() {
        if (Objects.nonNull(userSessionHolder.get())) {
            return "redirect:/todos";
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@Valid LoginCommand command, Model model) {
        log.debug("login command: {}", command);

        User user;
        try {
            // 1. 사용자 저장소에 사용자가 있을 경우: 비밀번호 확인 후 로그인 처리
            user = verifyUserPassword.verify(command.username(), command.password());
        } catch (UserNotFoundException error) {
            // 2. 사용자가 없는 경우: 회원가입 처리 후 로그인 처리
            user = registerUser.register(command.username(), command.password());
        } catch (UserPasswordNotMatchedException error) {
            // 3. 비밀번호가 틀린 경우: login 페이지로 돌려보내고, 오류 메시지 노출
            model.addAttribute("message", error.getMessage());
            return "login";
        }
        userSessionHolder.set(new UserSession(user));

        return "redirect:/todos";
    }

    @RequestMapping("/logout")
    public View logout() {
        userSessionHolder.reset();
        return new RedirectView("/todos");
    }

    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException error, Model model) {
        model.addAttribute("bindingResult", error.getBindingResult());
        model.addAttribute("message", "입력 값이 없거나 올바르지 않아요.");
        return "login";
    }

    record LoginCommand(@Size(min = 4, max = 20) String username, String password) {

    }

}
