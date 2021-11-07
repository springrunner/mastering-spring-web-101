package todoapp.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import todoapp.commons.SystemException;

/**
 * 권한이 없어 접근 불가 상황시 발생 가능한 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends SystemException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException() {
        super("접근을 거부합니다.");
    }

}
