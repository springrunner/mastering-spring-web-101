package todoapp.core.todo.application;

import todoapp.core.todo.domain.TodoEntityNotFoundException;
import todoapp.core.todo.domain.TodoId;

/**
 * 등록된 할일을 정리하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoCleanup {

    /**
     * 등록된 할일을 삭제한다.
     *
     * @param id 할일 Id
     */
    void clear(TodoId id) throws TodoEntityNotFoundException;

}
