package todoapp.core.user.application;

import todoapp.core.user.domain.User;

/**
 * 사용자 가입을 처리하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface RegisterUser {

    /**
     * 새로운 사용자를 등록한다. 동일한 이름에 사용자가 존재하면 예외가 발생한다.
     *
     * @param username    사용자 이름
     * @param rawPassword 원시 비밀번호
     * @return 등록된 사용자 객체
     */
    User register(String username, String rawPassword);

}
