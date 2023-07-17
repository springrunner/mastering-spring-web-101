package todoapp.core.foundation;

import org.springframework.context.MessageSourceResolvable;

/**
 * 시스템 운용 중 발생 가능한 최상위 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class SystemException extends RuntimeException implements MessageSourceResolvable {

    public SystemException(String format, Object... args) {
        super(String.format(format, args));
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String[] getCodes() {
        return new String[]{"Exception." + getClass().getSimpleName()};
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public String getDefaultMessage() {
        return getMessage();
    }

}
