package todoapp.core.foundation.crypto.support;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import todoapp.core.foundation.crypto.PasswordEncoder;

import java.nio.charset.Charset;

/**
 * {@link PasswordEncoder} 기본 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Component
public class SimplePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(Charset.defaultCharset()));
    }

}
