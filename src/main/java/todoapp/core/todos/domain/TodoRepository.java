package todoapp.core.todos.domain;

import java.util.List;
import java.util.Optional;

/**
 * 할 일 저장소 인터페이스
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoRepository {

    /**
     * 모든 할 일 목록을 반환한다.
     * 등록된 할 일이 없으면 빈 목록을 반환한다.
     *
     * @return List<Todo> 개체
     */
    List<Todo> findAll();

    /**
     * 해당 사용자 이름으로 등록된 모든 할 일 목록을 반환한다.
     * 등록된 할 일이 없으면 빈 목록을 반환한다.
     *
     * @param username 사용자 이름
     * @return List<Todo> 개체
     */
    List<Todo> findByUsername(String username);

    /**
     * 할 일 번호로 할 일을 찾는다.
     * 일치하는 할 일이 없으면 Optional.empty()가 반환된다.
     *
     * @param id 할 일 번호
     * @return Optional<Todo> 개체
     */
    Optional<Todo> findById(Long id);

    /**
     * 저장소에 할 일 개체를 저장한다.
     *
     * @param todo 할 일 개체
     * @return 저장된 할 일 개체
     */
    Todo save(Todo todo);

    /**
     * 저장소에 할 일 개체를 제거한다.
     * 일치하는 할 일이 없으면 무시한다.
     *
     * @param todo 삭제할 할 일 개체
     */
    void delete(Todo todo);

}
