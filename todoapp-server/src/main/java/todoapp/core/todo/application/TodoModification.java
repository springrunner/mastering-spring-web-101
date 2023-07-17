package todoapp.core.todo.application;

/**
 * 할일 수정(변경)하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoModification {

  /**
   * 등록된 할일을 수정한다.
   *
   * @param id 할일 번호
   * @param title 할일
   * @param completed 완료여부
   */
  void modify(Long id, String title, boolean completed);

}
