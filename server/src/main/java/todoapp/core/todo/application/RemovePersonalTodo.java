package todoapp.core.todo.application;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.shared.identifier.UserId;

/**
 * 개인화된 할일 정리 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface RemovePersonalTodo extends RemoveTodo {

    /**
     * 사용자가 등록한 할일을 정리(삭제)한다.
     *
     * @param owner 소유자 식별자
     * @param id    할일 식별자
     */
    void remove(UserId owner, TodoId id);

}
