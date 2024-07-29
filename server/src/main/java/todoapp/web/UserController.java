package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@Controller
public class UserController {

    private final ProfilePictureStorage profilePictureStorage;

    public UserController(ProfilePictureStorage profilePictureStorage) {
        this.profilePictureStorage = Objects.requireNonNull(profilePictureStorage);
    }

    @RolesAllowed(UserSession.ROLE_USER)
    @RequestMapping("/user/profile-picture")
    public @ResponseBody Resource profilePicture(UserSession session) {
        return profilePictureStorage.load(session.getUser().getProfilePicture().getUri());
    }

}
