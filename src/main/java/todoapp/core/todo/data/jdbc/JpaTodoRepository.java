package todoapp.core.todo.data.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import todoapp.Constant;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoId;
import todoapp.core.todo.domain.TodoRepository;

/**
 * Spring Data JPA 기반 할일 저장소 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Profile(Constant.PROFILE_PRODUCTION)
interface JpaTodoRepository extends TodoRepository, JpaRepository<Todo, TodoId> {

}
