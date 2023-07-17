package todoapp.core.todo.application;

/**
 * 새로운 할일을 등록하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoRegistry {

  /**
   * 새로운 할일을 등록한다.
   *
   * @param title 할일
   * @return 생성된 할일 객체
   */
  void register(String title);
}
