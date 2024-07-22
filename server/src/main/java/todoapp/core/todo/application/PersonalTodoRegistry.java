package todoapp.core.todo.application;

import todoapp.core.todo.domain.TodoId;
import todoapp.core.todo.domain.TodoRegistrationException;
import todoapp.core.user.domain.User;

/**
 * 개인화된 할일 등록 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoRegistry {

    /**
     * 사용자의 새로운 할일을 등록한다.
     *
     * @param user 사용자
     * @param text 할일
     * @return 생성된 할일 일련번호
     */
    TodoId register(User user, String text) throws TodoRegistrationException;

}
