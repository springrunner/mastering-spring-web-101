package todoapp.core.todo;

import todoapp.core.shared.identifier.TodoId;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoIdGenerator;

import java.util.UUID;

/**
 * @author springrunner.kr@gmail.com
 */
public class TodoFixture {

    private static final TodoIdGenerator idGenerator = () -> TodoId.of(UUID.randomUUID().toString());

    public static Todo random() {
        return Todo.create("Task#" + System.nanoTime(), idGenerator);
    }

}
