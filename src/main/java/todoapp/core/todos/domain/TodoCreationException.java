package todoapp.core.todos.domain;

/**
 * 할 일 생성하는 과정에서 발생가능한 예외
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoCreationException extends TodoEntityException {
   
    private static final long serialVersionUID = 1L;

    public TodoCreationException(String format, Object...args) {
        super(String.format(format, args));
    }

}
