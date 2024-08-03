package todoapp.core.user.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.core.user.domain.*;

import java.util.Objects;

/**
 * 사용자 서비스를 위한 유스케이스 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
class DefaultUserService implements UserPasswordVerifier, UserRegistration, ProfilePictureChanger {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    public User verify(String username, String rawPassword) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserEntityNotFoundException(username))
                .verifyPassword(passwordEncoder.encode(rawPassword));
    }

    public User join(String username, String rawPassword) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            var user = userRepository.save(new User(username, passwordEncoder.encode(rawPassword)));
            log.info("new user has joined (username: {})", user);

            return user;
        });
    }

    @Override
    public User change(String username, ProfilePicture profilePicture) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserEntityNotFoundException(username)).changeProfilePicture(profilePicture);
    }

}
