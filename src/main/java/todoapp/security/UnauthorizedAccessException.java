package todoapp.security;

import todoapp.commons.SystemException;

/**
 * 인증되지 않은 사용자 접근시 발생 가능한 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public class UnauthorizedAccessException extends SystemException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedAccessException() {
        super("인증되지 않은 접근입니다.");
    }

}
