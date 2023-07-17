package todoapp.core.todo.application;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.todo.domain.TodoNotFoundException;

/**
 * 등록된 할일을 삭제하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface RemoveTodo {

    /**
     * 등록된 할일을 삭제한다.
     *
     * @param id 할일 Id
     */
    void remove(TodoId id) throws TodoNotFoundException;

}
