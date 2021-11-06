package todoapp.core.user.application;

import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserPasswordNotMatchedException;

/**
 * 사용자 비밀번호 검증기 인터페이스
 *
 * @author springrunner.kr@gmail.com
 */
public interface UserPasswordVerifier {

    /**
     * 사용자 이름과 일치하는 사용자를 찾아 비밀번호를 확인한다.
     * 비밀번호가 일치하지 않으면 예외가 발생한다.
     *
     * @param username 사용자 이름
     * @param rawPassword 비밀번호
     * @return 사용자 개체
     */
    User verify(String username, String rawPassword) throws UserPasswordNotMatchedException;

}
