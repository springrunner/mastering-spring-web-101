package todoapp.core.todo.converter.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;
import todoapp.core.todo.domain.TodoId;

/**
 * todo 모듈을 지원하기 위해 작성된 Jackson2 확장 모듈이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Component
public class TodoModule extends SimpleModule {

    public TodoModule() {
        super("todo-module");

        addSerializer(TodoId.class, Jackson2TodoIdSerdes.SERIALIZER);
        addDeserializer(TodoId.class, Jackson2TodoIdSerdes.DESERIALIZER);
    }

}
