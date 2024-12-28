package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.security.UserSession;

/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class UserController {

    @RolesAllowed(UserSession.ROLE_USER)
    @RequestMapping("/user/profile-picture")
    public ProfilePicture profilePicture(UserSession session) {
        return session.getUser().getProfilePicture();
    }

}
