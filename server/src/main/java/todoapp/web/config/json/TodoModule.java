package todoapp.web.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import todoapp.core.shared.identifier.TodoId;

import java.io.IOException;

/**
 * todo 모듈을 지원하기 위해 작성된 Jackson2 확장 모듈이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Component
class TodoModule extends SimpleModule {

    TodoModule() {
        super("todo-module");

        addSerializer(TodoId.class, Jackson2TodoIdSerdes.SERIALIZER);
        addDeserializer(TodoId.class, Jackson2TodoIdSerdes.DESERIALIZER);
    }

    /**
     * Jackson2 라이브러리에서 사용할 할일 식별자 직렬화/역직렬화 처리기
     *
     * @author springrunner.kr@gmail.com
     */
    static class Jackson2TodoIdSerdes {

        static final TodoIdSerializer SERIALIZER = new TodoIdSerializer();
        static final TodoIdDeserializer DESERIALIZER = new TodoIdDeserializer();

        static class TodoIdSerializer extends StdSerializer<TodoId> {

            TodoIdSerializer() {
                super(TodoId.class);
            }

            @Override
            public void serialize(TodoId id, JsonGenerator generator, SerializerProvider provider) throws IOException {
                generator.writeString(id.toString());
            }

        }

        static class TodoIdDeserializer extends StdDeserializer<TodoId> {

            TodoIdDeserializer() {
                super(TodoId.class);
            }

            @Override
            public TodoId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                return TodoId.of(parser.readValueAs(String.class));
            }

        }

    }

}
