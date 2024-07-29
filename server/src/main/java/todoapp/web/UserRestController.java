package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;
import todoapp.web.model.UserProfile;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@RestController
public class UserRestController {

    private final ProfilePictureStorage profilePictureStorage;
    private final ProfilePictureChanger profilePictureChanger;
    private final UserSessionHolder userSessionHolder;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserRestController(ProfilePictureStorage profilePictureStorage, ProfilePictureChanger profilePictureChanger, UserSessionHolder userSessionHolder) {
        this.profilePictureStorage = Objects.requireNonNull(profilePictureStorage);
        this.profilePictureChanger = Objects.requireNonNull(profilePictureChanger);
        this.userSessionHolder = Objects.requireNonNull(userSessionHolder);
    }

    @RolesAllowed(UserSession.ROLE_USER)
    @GetMapping("/api/user/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

    @RolesAllowed(UserSession.ROLE_USER)
    @PostMapping("/api/user/profile-picture")
    public UserProfile changeProfilePicture(MultipartFile profilePicture, UserSession session) {
        log.debug("profilePicture: {}, {}", profilePicture.getOriginalFilename(), profilePicture.getContentType());

        // 업로드된 프로필 이미지 파일 저장하기
        var profilePictureUri = profilePictureStorage.save(profilePicture.getResource());

        // 프로필 이미지 변경 후 세션 갱신하기
        var updatedUser = profilePictureChanger.change(session.getName(), new ProfilePicture(profilePictureUri));
        userSessionHolder.set(new UserSession(updatedUser));

        return new UserProfile(updatedUser);
    }

}
