package todoapp.core.todo.application;

/**
 * 등록된 할일을 정리하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoCleanup {

  /**
   * 등록된 할일을 정리(삭제)한다.
   *
   * @param id 할일 번호
   */
  void clear(Long id);

}
