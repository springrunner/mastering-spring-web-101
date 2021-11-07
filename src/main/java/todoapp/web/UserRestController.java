package todoapp.web;

import java.net.URI;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {

    private final ProfilePictureStorage profilePictureStorage;
    private final ProfilePictureChanger profilePictureChanger;
    private final UserSessionRepository userSessionRepository;
    private final Logger log = LoggerFactory.getLogger(UserRestController.class);
    
    public UserRestController(ProfilePictureStorage profilePictureStorage, ProfilePictureChanger profilePictureChanger, UserSessionRepository userSessionRepository) {
        this.profilePictureStorage = Objects.requireNonNull(profilePictureStorage);
        this.profilePictureChanger = Objects.requireNonNull(profilePictureChanger);
        this.userSessionRepository = Objects.requireNonNull(userSessionRepository);
    }

    @GetMapping("/api/user/profile")
    @RolesAllowed(UserSession.ROLE_USER)
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }
    
    @PostMapping("/api/user/profile-picture")
    @RolesAllowed(UserSession.ROLE_USER)
    public UserProfile changePorfilePicture(MultipartFile profilePicture, UserSession session) {
        log.debug("profilePicture: {}, {}", profilePicture.getOriginalFilename(), profilePicture.getContentType());
        
        // 업로드된 프로필 이미지 파일 저장하기
        URI profilePictureUri = profilePictureStorage.save(profilePicture.getResource());
        
        // 프로필 이미지 변경 후 세션 갱신하기
        User updatedUser = profilePictureChanger.change(session.getName(), new ProfilePicture(profilePictureUri));
        userSessionRepository.set(new UserSession(updatedUser));
        
        return new UserProfile(updatedUser);
    }
    
}
