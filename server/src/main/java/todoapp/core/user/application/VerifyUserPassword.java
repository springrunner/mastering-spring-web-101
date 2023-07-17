package todoapp.core.user.application;

import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;

/**
 * 사용자 비밀번호 검증을 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface VerifyUserPassword {

    /**
     * 사용자 이름과 일치하는 사용자의 비밀번호를 확인한다.
     * 비밀번호가 일치하지 않으면 예외가 발생한다.
     *
     * @param username    사용자 이름
     * @param rawPassword 비밀번호
     * @return 사용자 객체
     */
    User verify(String username, String rawPassword) throws UserNotFoundException, UserPasswordNotMatchedException;

}
