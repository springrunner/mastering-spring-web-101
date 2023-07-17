package todoapp.core.todo.application;

import todoapp.core.todo.domain.TodoId;
import todoapp.core.user.domain.User;

/**
 * 개인화된 할일 수정(변경) 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoModification extends TodoModification {

    /**
     * 사용자가 등록한 할일을 수정한다.
     *
     * @param user      사용자 객체
     * @param id        할일 번호
     * @param text     할일
     * @param completed 완료여부
     */
    void modify(User user, TodoId id, String text, boolean completed);

}
