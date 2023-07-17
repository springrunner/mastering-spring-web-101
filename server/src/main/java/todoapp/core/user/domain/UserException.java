package todoapp.core.user.domain;

import todoapp.core.foundation.SystemException;

/**
 * 사용자 엔티티에서 발생 가능한 최상위 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class UserException extends SystemException {

    public UserException(String format, Object... args) {
        super(format, args);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
