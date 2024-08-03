package todoapp.core.todo.application;

import todoapp.core.todo.domain.TodoId;
import todoapp.core.user.domain.User;

/**
 * 개인화된 할일 정리 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoCleanup extends TodoCleanup {

    /**
     * 사용자가 등록한 할일을 정리(삭제)한다.
     *
     * @param user 사용자 객체
     * @param id   할일 일련번호
     */
    void clear(User user, TodoId id);

}
