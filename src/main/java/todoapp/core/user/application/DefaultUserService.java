package todoapp.core.user.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapp.core.foundation.crypto.PasswordEncoder;
import todoapp.core.user.domain.*;

import java.util.Objects;

/**
 * 사용자 서비스를 위한 유스케이스 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
class DefaultUserService implements VerifyUserPassword, RegisterUser, ChangeUserProfilePicture {

    private final UserIdGenerator userIdGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    DefaultUserService(UserIdGenerator userIdGenerator, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userIdGenerator = Objects.requireNonNull(userIdGenerator);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    public User verify(String username, String rawPassword) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username))
                .verifyPassword(passwordEncoder.encode(rawPassword));
    }

    public User register(String username, String rawPassword) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            var user = userRepository.save(new User(userIdGenerator.generateId(), username, passwordEncoder.encode(rawPassword)));
            log.info("new user has joined (username: {})", user);

            return user;
        });
    }

    @Override
    public User change(String username, ProfilePicture profilePicture) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username))
                .changeProfilePicture(profilePicture);
    }

}
