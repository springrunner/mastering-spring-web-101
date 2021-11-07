package todoapp.web;

import javax.annotation.security.RolesAllowed;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;

@Controller
public class UserController {

    private ProfilePictureStorage profilePictureStorage;

    public UserController(ProfilePictureStorage profilePictureStorage) {
        this.profilePictureStorage = profilePictureStorage;
    }

    @RequestMapping("/user/profile-picture")
    @RolesAllowed(UserSession.ROLE_USER)
    public @ResponseBody Resource profilePicture(UserSession session) {
        return profilePictureStorage.load(session.getUser().getProfilePicture().getUri());
    }
    
}
