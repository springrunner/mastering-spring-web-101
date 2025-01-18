package todoapp.web.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import todoapp.core.shared.identifier.UserId;

import java.io.IOException;

/**
 * user 모듈을 지원하기 위해 작성된 Jackson2 확장 모듈이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Component
class UserModule extends SimpleModule {

    UserModule() {
        super("user-module");

        addSerializer(UserId.class, Jackson2UserIdSerdes.SERIALIZER);
        addDeserializer(UserId.class, Jackson2UserIdSerdes.DESERIALIZER);
    }

    /**
     * Jackson2 라이브러리에서 사용할 할일 식별자 직렬화/역직렬화 처리기
     *
     * @author springrunner.kr@gmail.com
     */
    static class Jackson2UserIdSerdes {

        static final UserIdSerializer SERIALIZER = new UserIdSerializer();
        static final UserIdDeserializer DESERIALIZER = new UserIdDeserializer();

        static class UserIdSerializer extends StdSerializer<UserId> {

            UserIdSerializer() {
                super(UserId.class);
            }

            @Override
            public void serialize(UserId id, JsonGenerator generator, SerializerProvider provider) throws IOException {
                generator.writeString(id.toString());
            }

        }

        static class UserIdDeserializer extends StdDeserializer<UserId> {

            UserIdDeserializer() {
                super(UserId.class);
            }

            @Override
            public UserId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                return UserId.of(parser.readValueAs(String.class));
            }

        }

    }

}
