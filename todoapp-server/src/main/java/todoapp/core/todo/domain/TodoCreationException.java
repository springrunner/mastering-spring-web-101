package todoapp.core.todo.domain;

/**
 * 할일 생성하는 과정에서 발생가능한 예외 클래이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoCreationException extends TodoEntityException {

  public TodoCreationException(String format, Object... args) {
    super(String.format(format, args));
  }

}
