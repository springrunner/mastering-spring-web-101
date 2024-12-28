package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import todoapp.core.user.application.ChangeUserProfilePicture;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;
import todoapp.web.model.UserProfile;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RolesAllowed(UserSession.ROLE_USER)
@RestController
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProfilePictureStorage profilePictureStorage;
    private final ChangeUserProfilePicture changeUserProfilePicture;
    private final UserSessionHolder userSessionHolder;

    public UserRestController(ProfilePictureStorage profilePictureStorage, ChangeUserProfilePicture changeUserProfilePicture, UserSessionHolder userSessionHolder) {
        this.profilePictureStorage = Objects.requireNonNull(profilePictureStorage);
        this.changeUserProfilePicture = Objects.requireNonNull(changeUserProfilePicture);
        this.userSessionHolder = Objects.requireNonNull(userSessionHolder);
    }

    @GetMapping("/api/user/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

    @PostMapping("/api/user/profile-picture")
    public UserProfile changeProfilePicture(MultipartFile profilePicture, UserSession session) {
        log.debug("profilePicture: {}, {}", profilePicture.getOriginalFilename(), profilePicture.getContentType());

        // 업로드된 프로필 이미지 파일 저장하기
        var profilePictureUri = profilePictureStorage.save(profilePicture.getResource());

        // 프로필 이미지 변경 후 세션 갱신하기
        var updatedUser = changeUserProfilePicture.change(session.getName(), new ProfilePicture(profilePictureUri));
        userSessionHolder.set(new UserSession(updatedUser));

        return new UserProfile(updatedUser);
    }

}
