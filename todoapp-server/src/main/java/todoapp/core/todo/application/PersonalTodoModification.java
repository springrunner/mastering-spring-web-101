package todoapp.core.todo.application;

import todoapp.core.user.domain.User;

/**
 * 할일 편집기 인터페이스로 
 *
 * @author springrunner.kr@gmail.com
 */
public interface PersonalTodoModification extends TodoModification {

  /**
   * 사용자가 등록한 할일을 수정한다.
   *
   * @param user 사용자 객체
   * @param id 할일 번호
   * @param title 할일
   * @param completed 완료여부
   */
  void modify(User user, Long id, String title, boolean completed);

}
