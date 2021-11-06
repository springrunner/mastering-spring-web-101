package todoapp.core.todos.application;

import todoapp.core.user.domain.User;

/**
 * 할 일 편집기 인터페이스
 * 특정 사용자에 대한 할 일을 등록, 변경, 제거한다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoEditor {

    /**
     * 새로운 할 일 을 등록한다.
     *
     * @param user 사용자 개체
     * @param title 할 일
     * @return 생성된 할 일 개체
     */
    void create(User user, String title);

    /**
     * 해당 할 일 번호로 등록된 할 일을 변경한다.
     *
     * @param user 사용자 개체
     * @param id 할 일 번호
     * @param title 할 일
     * @param completed 완료여부
     * @return 변경된 할 일 개체
     */
    void update(User user, Long id, String title, boolean completed);

    /**
     * 해당 할 일 번호로 등록된 할 일을 삭제한다.
     *
     * @param user 사용자 개체
     * @param id 할 일 번호
     * @return 삭제된 할 일 개체
     */
    void delete(User user, Long id);

}
