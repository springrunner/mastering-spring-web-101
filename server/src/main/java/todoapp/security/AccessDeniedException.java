package todoapp.security;

import todoapp.core.foundation.SystemException;

/**
 * 권한이 없어 접근 불가 상황시 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class AccessDeniedException extends SystemException {

    public AccessDeniedException() {
        super("Access denied: insufficient permissions to access the resource");
    }

}
