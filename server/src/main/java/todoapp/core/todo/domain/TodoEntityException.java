package todoapp.core.todo.domain;

import todoapp.commons.SystemException;

/**
 * 할일 엔티티에서 발생 가능한 최상위 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoEntityException extends SystemException {

    public TodoEntityException(String format, Object... args) {
        super(String.format(format, args));
    }

}
