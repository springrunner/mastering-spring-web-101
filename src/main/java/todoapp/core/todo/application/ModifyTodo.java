package todoapp.core.todo.application;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.todo.domain.TodoNotFoundException;

/**
 * 할일 수정(변경)하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface ModifyTodo {

    /**
     * 등록된 할일을 수정한다.
     *
     * @param id        할일 식별자
     * @param text      할일 내용
     * @param completed 완료여부
     */
    void modify(TodoId id, String text, boolean completed) throws TodoNotFoundException;

}
