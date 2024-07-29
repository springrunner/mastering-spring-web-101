package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

/**
 * @author springrunner.kr@gmail.com
 */
@RestController
public class UserRestController {

    @RolesAllowed(UserSession.ROLE_USER)
    @GetMapping("/api/user/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

}
