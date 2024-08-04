package todoapp.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import todoapp.commons.SystemException;

/**
 * 인증되지 않은 사용자 접근시 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends SystemException {

    public UnauthorizedAccessException() {
        super("Unauthorized access: You must be authenticated to access this resource");
    }

}
