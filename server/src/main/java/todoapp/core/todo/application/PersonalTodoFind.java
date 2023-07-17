package todoapp.core.todo.application;

import todoapp.core.todo.domain.Todo;
import todoapp.core.user.domain.User;

import java.util.List;

/**
 * 개인화된 할일 검색 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoFind extends TodoFind {

    /**
     * 해당 사용자로 등록된 모든 할일 목록을 반환한다. 할일이 없으면 빈 목록을 반환한다.
     *
     * @param user 사용자 객체
     * @return List<Todo> 객체
     */
    List<Todo> all(User user);

}
