package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.security.UserSession;

@Controller
public class UserController {

    @RequestMapping("/user/profile-picture")
    @RolesAllowed(UserSession.ROLE_USER)
    public ProfilePicture profilePicture(UserSession session) {
        return session.getUser().getProfilePicture();
    }

}
