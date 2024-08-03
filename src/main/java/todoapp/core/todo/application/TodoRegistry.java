package todoapp.core.todo.application;

import todoapp.core.todo.domain.TodoId;
import todoapp.core.todo.domain.TodoRegistrationException;

/**
 * 새로운 할일을 등록하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoRegistry {

    /**
     * 새로운 할일을 등록한다.
     *
     * @param text 할일
     * @return 생성된 할일 일련번호
     */
    TodoId register(String text) throws TodoRegistrationException;

}
