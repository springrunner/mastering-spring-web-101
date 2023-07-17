package todoapp.core.todo.application;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.shared.identifier.UserId;

/**
 * 개인화된 할일 수정(변경) 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface ModifyPersonalTodo extends ModifyTodo {

    /**
     * 사용자가 등록한 할일을 수정한다.
     *
     * @param owner     소유자 식별자
     * @param id        할일 번호
     * @param text      할일
     * @param completed 완료여부
     */
    void modify(UserId owner, TodoId id, String text, boolean completed);

}
