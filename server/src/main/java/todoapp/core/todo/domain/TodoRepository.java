package todoapp.core.todo.domain;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.shared.identifier.UserId;

import java.util.List;
import java.util.Optional;

/**
 * 할일 저장소 인터페이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoRepository {

    /**
     * 모든 할일 목록을 반환한다. 등록된 할일이 없으면 빈 목록을 반환한다.
     *
     * @return List<Todo> 객체
     */
    List<Todo> findAll();

    /**
     * 해당 사용자로 등록된 모든 할일 목록을 반환한다. 등록된 할일이 없으면 빈 목록을 반환한다.
     *
     * @param owner 소유자(사용자)
     * @return List<Todo> 객체
     */
    List<Todo> findByOwner(UserId owner);

    /**
     * 할일 식별자로 할일을 찾는다. 일치하는 할일이 없으면 Optional.empty()가 반환된다.
     *
     * @param id 할일 식별자
     * @return Optional<Todo> 객체
     */
    Optional<Todo> findById(TodoId id);

    /**
     * 저장소에 할일 객체를 저장한다.
     *
     * @param todo 할일 객체
     * @return 저장된 할일 객체
     */
    Todo save(Todo todo);

    /**
     * 저장소에 할일 객체를 제거한다. 일치하는 할일이 없으면 무시한다.
     *
     * @param todo 삭제할 할일 객체
     */
    void delete(Todo todo);

}
