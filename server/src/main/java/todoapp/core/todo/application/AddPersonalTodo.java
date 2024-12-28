package todoapp.core.todo.application;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.shared.identifier.UserId;
import todoapp.core.todo.domain.TodoRegistrationRejectedException;

/**
 * 개인화된 할일 등록 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface AddPersonalTodo extends AddTodo {

    /**
     * 사용자의 새로운 할일을 등록한다.
     *
     * @param owner 소유자 식별자
     * @param text  할일
     * @return 등록된 할일 식별자
     */
    TodoId register(UserId owner, String text) throws TodoRegistrationRejectedException;

}
