package todoapp.core.user.domain;

import java.util.Optional;

/**
 * 사용자 저장소 인터페이스
 *
 * @author springrunner.kr@gmail.com
 */
public interface UserRepository {

    /**
     * 사용자 이름으로 사용자를 찾는다.
     * 일치하는 사용자가 없으면 Optional.empty()가 반환된다.
     *
     * @param username 사용자 이름
     * @return Optional<User> 개체
     */
    Optional<User> findByUsername(String username);

    /**
     * 저장소에 사용자 개체를 저장한다.
     *
     * @param user 사용자 개체
     * @return 저장된 사용자 개체
     */
    User save(User user);

}
