package todoapp.core.todos.application;

import todoapp.core.todos.domain.Todo;
import todoapp.core.user.domain.User;

import java.util.List;

/**
 * 할 일 검색기 인터페이스
 * 특정 사용자에 대한 할 일을 검색한다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoFinder {

    /**
     * 해당 사용자로 등록된 모든 할 일 목록을 반환한다.
     * 할 일이 없으면 빈 목록을 반환한다.
     *
     * @param user 사용자 개체
     * @return List<Todo> 개체
     */
    List<Todo> getAll(User user);

}
