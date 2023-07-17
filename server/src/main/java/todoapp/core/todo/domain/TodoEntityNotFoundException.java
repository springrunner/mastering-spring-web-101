package todoapp.core.todo.domain;

/**
 * 할일 저장소에서 할일 엔티티를 찾을 수 없을 때 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoEntityNotFoundException extends TodoEntityException {

    private final TodoId id;

    public TodoEntityNotFoundException(TodoId id) {
        super("todo entity not found (id: %s)", id);
        this.id = id;
    }

    public TodoId getId() {
        return id;
    }

}
