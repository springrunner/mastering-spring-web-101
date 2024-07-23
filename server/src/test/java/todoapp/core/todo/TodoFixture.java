package todoapp.core.todo;

import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoIdGenerator;
import todoapp.core.todo.domain.support.UUIDTodoIdGenerator;

/**
 * @author springrunner.kr@gmail.com
 */
public class TodoFixture {

    private static final TodoIdGenerator idGenerator = new UUIDTodoIdGenerator();

    public static Todo random() {
        return Todo.create("Task#" + System.nanoTime(), idGenerator);
    }
}
