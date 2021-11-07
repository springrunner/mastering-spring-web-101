package todoapp.core.todos.domain;

import todoapp.commons.SystemException;

/**
 * 할 일 엔티티에서 발생 가능한 최상위 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoEntityException extends SystemException {
    
    private static final long serialVersionUID = 1L;

    public TodoEntityException(String format, Object... args) {
        super(String.format(format, args));
    }

}
