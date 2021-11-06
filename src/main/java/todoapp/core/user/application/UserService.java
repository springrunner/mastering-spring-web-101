package todoapp.core.user.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.core.user.domain.*;

/**
 * 사용자 서비스 구현체
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
public class UserService implements UserPasswordVerifier, UserRegistration, ProfilePictureChanger {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User verify(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UserEntityNotFoundException(username))
                             .verifyPassword(passwordEncoder.encode(rawPassword));
    }

    public User join(String username, String rawPassword) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User user = userRepository.save(new User(username, passwordEncoder.encode(rawPassword)));
            log.info("new user joining: {}", user);

            return user;
        });
    }

    @Override
    public User change(String username, ProfilePicture profilePicture) {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UserEntityNotFoundException(username))
                             .changeProfilePicture(profilePicture);
    }

}
