package todoapp.core.todo.domain;

import todoapp.core.shared.identifier.TodoId;

/**
 * 할일 식별자 생성기
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoIdGenerator {

    TodoId generateId();

}
