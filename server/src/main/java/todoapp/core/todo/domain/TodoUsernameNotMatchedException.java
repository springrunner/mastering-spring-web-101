package todoapp.core.todo.domain;

/**
 * 할일을 편집할 때 작성자가 일치하지 않으면 발생하는 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoUsernameNotMatchedException extends TodoEntityException {

    public TodoUsernameNotMatchedException() {
        super("mismatched username");
    }

}
