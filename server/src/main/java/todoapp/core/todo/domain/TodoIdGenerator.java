package todoapp.core.todo.domain;

/**
 * 할일 일련번호 생성기
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoIdGenerator {

    TodoId generateId();

}
