package todoapp.core.todo.application;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.todo.domain.TodoRegistrationRejectedException;

/**
 * 새로운 할일을 등록하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface AddTodo {

    /**
     * 새로운 할일을 등록한다.
     *
     * @param text 할일
     * @return 등록된 할일 식별자
     */
    TodoId add(String text) throws TodoRegistrationRejectedException;

}
