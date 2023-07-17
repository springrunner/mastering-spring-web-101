package todoapp.core.todo.application;

import todoapp.core.todo.domain.TodoEntityNotFoundException;
import todoapp.core.todo.domain.TodoId;

/**
 * 할일 수정(변경)하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoModification {

    /**
     * 등록된 할일을 수정한다.
     *
     * @param id        할일 일련번호
     * @param text      할일 내용
     * @param completed 완료여부
     */
    void modify(TodoId id, String text, boolean completed) throws TodoEntityNotFoundException;

}
