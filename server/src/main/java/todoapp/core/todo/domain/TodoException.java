package todoapp.core.todo.domain;

/**
 * 할일 도메인에서 발생 가능한 최상위 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoException extends RuntimeException {

    public TodoException(String format, Object... args) {
        super(String.format(format, args));
    }

}
