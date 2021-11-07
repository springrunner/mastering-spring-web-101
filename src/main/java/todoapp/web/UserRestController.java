package todoapp.web;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {

    @GetMapping("/api/user/profile")
    @RolesAllowed(UserSession.ROLE_USER)
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }
    
}
