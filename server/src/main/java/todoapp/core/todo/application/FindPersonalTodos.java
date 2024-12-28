package todoapp.core.todo.application;

import todoapp.core.shared.identifier.UserId;
import todoapp.core.todo.domain.Todo;

import java.util.List;

/**
 * 개인화된 할일 검색 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface FindPersonalTodos extends FindTodos {

    /**
     * 해당 소유자로 등록된 모든 할일 목록을 반환한다. 할일이 없으면 빈 목록을 반환한다.
     *
     * @param owner 소유자 식별자
     * @return List<Todo> 할일 목록 객체
     */
    List<Todo> all(UserId owner);

}
