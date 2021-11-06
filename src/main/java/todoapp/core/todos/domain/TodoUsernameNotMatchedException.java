package todoapp.core.todos.domain;

/**
 * 할 일을 편집할 때 작성자가 일치하지 않으면 발생하는 예외
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoUsernameNotMatchedException extends TodoEntityException {

    private static final long serialVersionUID = 1L;

    public TodoUsernameNotMatchedException() {
        super("작성자가 일치하지 않습니다.");
    }

}
