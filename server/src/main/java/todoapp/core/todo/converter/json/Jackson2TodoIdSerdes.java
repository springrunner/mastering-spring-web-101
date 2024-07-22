package todoapp.core.todo.converter.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import todoapp.core.todo.domain.TodoId;

import java.io.IOException;

/**
 * Jackson2 라이브러리에서 사용할 할일 일련번호 직렬화/역직렬화 처리기
 *
 * @author springrunner.kr@gmail.com
 */
class Jackson2TodoIdSerdes {

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
            return new TodoId(parser.readValueAs(String.class));
        }

    }

}
