package todoapp.core.todo.domain;

/**
 * 할일을 등록하는 과정에서 발생가능한 예외 클래이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoRegistrationRejectedException extends TodoException {

    public TodoRegistrationRejectedException(String format, Object... args) {
        super(String.format(format, args));
    }

}
