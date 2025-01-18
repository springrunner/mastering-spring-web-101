package todoapp.data;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoIdGenerator;
import todoapp.core.todo.domain.TodoRepository;

import java.util.Objects;

/**
 * @author springrunner.kr@gmail.com
 */
@Component
@ConditionalOnProperty(name = "todoapp.data.initialize", havingValue = "true")
class TodoDataInitializer implements InitializingBean, ApplicationRunner, CommandLineRunner {

    private final TodoIdGenerator todoIdGenerator;
    private final TodoRepository todoRepository;

    public TodoDataInitializer(TodoIdGenerator todoIdGenerator, TodoRepository todoRepository) {
        this.todoIdGenerator = Objects.requireNonNull(todoIdGenerator);
        this.todoRepository = Objects.requireNonNull(todoRepository);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 1. InitializingBean
        // 스프링 컨테이너가 빈의 모든 프로퍼티를 설정한 후에 실행합니다.

        todoRepository.save(Todo.create("Task one", todoIdGenerator));
    }

    //

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 2. ApplicationRunner
        // 애플리케이션 시작 시점에 전달되는 커맨드 라인 인자(`args`)를 활용하여 필요한 로직을 실행합니다.

        todoRepository.save(Todo.create("Task two", todoIdGenerator));
    }

    @Override
    public void run(String... args) throws Exception {
        // 3. CommandLineRunner
        // 애플리케이션 시작 시점에 전달되는 커맨드 라인 인자(`args`)를 활용하여 필요한 로직을 실행합니다.
        // `ApplicationRunner`와 유사하지만, 입력된 커맨드 라인 인자를 직접 다룰 수 있습니다.

        todoRepository.save(Todo.create("Task three", todoIdGenerator));
    }

}
