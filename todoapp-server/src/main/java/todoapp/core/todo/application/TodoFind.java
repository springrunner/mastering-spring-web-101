package todoapp.core.todo.application;

import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoEntityNotFoundException;
import todoapp.core.todo.domain.TodoId;

import java.util.List;

/**
 * 할일 검색하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoFind {

    /**
     * 등록된 모든 할일을 반환한다. 할일이 없으면 빈 목록을 반환한다.
     *
     * @return List<Todo> 객체
     */
    List<Todo> all();

    /**
     * 할일 일련번호로 할일을 찾아 반환한다.
     *
     * @return 할일 객체
     */
    Todo byId(TodoId id) throws TodoEntityNotFoundException;

}
