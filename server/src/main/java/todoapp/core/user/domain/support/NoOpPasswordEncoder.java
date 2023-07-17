package todoapp.core.user.domain.support;

import todoapp.core.user.domain.PasswordEncoder;

/**
 * 아무 처리를 하지 않는 {@link PasswordEncoder} 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class NoOpPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        return password;
    }

}
