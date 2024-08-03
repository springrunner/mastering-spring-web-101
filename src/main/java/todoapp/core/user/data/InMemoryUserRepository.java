package todoapp.core.user.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import todoapp.Constant;
import todoapp.core.user.domain.PasswordEncoder;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 메모리 기반 사용자 저장소 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Profile(Constant.PROFILE_DEVELOPMENT)
@Repository
class InMemoryUserRepository implements UserRepository, ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final List<User> users = new CopyOnWriteArrayList<>();
    private final Logger log = LoggerFactory.getLogger(getClass());

    public InMemoryUserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream().filter(user -> Objects.equals(user.getUsername(), username)).findAny();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        save(new User("user", passwordEncoder.encode("password")));
        log.info("enroll new user (username: user, password: password)");
    }

}
