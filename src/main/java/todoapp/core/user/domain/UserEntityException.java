package todoapp.core.user.domain;

import todoapp.commons.SystemException;

/**
 * 사용자 엔티티에서 발생 가능한 최상위 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public class UserEntityException extends SystemException {
    
    private static final long serialVersionUID = 1L;

    public UserEntityException(String format, Object... args) {
        super(format, args);
    }

    public UserEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
