package todoapp.commons.support;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.ClassUtils;
import todoapp.commons.context.ExceptionMessageTranslator;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 예외 객체를 {@link MessageSource}를 통해 적절한 메시지로 번역하는 컴포넌트이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class DefaultExceptionMessageTranslator implements ExceptionMessageTranslator {

    private final MessageSource messageSource;
    private final ExceptionCodeGenerator exceptionCodeGenerator;

    public DefaultExceptionMessageTranslator(MessageSource messageSource) {
        this(messageSource, throwable -> String.format("Exception.%s", ClassUtils.getShortName(throwable.getClass())));
    }
    
    public DefaultExceptionMessageTranslator(MessageSource messageSource, ExceptionCodeGenerator exceptionCodeGenerator) {
        this.messageSource = Objects.requireNonNull(messageSource);
        this.exceptionCodeGenerator = Objects.requireNonNull(exceptionCodeGenerator);
    }

    @Override
    public String getMessage(Throwable throwable, Locale locale) {
        return getMessage(throwable, throwable.getMessage(), locale);
    }

    @Override
    public String getMessage(Throwable throwable, String defaultMessage, Locale locale) {
        if (Objects.isNull(throwable)) {
            return defaultMessage;
        }
        
        if (ClassUtils.isAssignableValue(MessageSourceResolvable.class, throwable.getClass())) {
            return messageSource.getMessage((MessageSourceResolvable) throwable, locale);
        }

        Throwable rootCause = obtainRootCause(throwable);
        if (ClassUtils.isAssignableValue(MessageSourceResolvable.class, rootCause.getClass())) {
            return messageSource.getMessage((MessageSourceResolvable) rootCause, locale);
        }

        String code = exceptionCodeGenerator.generateExceptionCode(rootCause);
        return messageSource.getMessage(code, new Object[0], defaultMessage, locale);
    }
    
    public interface ExceptionCodeGenerator {
        String generateExceptionCode(Throwable throwable);
    }
    
    /**
     * 스택내 최상위 예외를 찾아 반환합니다.
     *
     * @param throwable 예외 객체
     * @return 최상위 예외 객체
     */
    private static Throwable obtainRootCause(Throwable throwable) {
        return Stream.iterate(throwable, Throwable::getCause)
                .filter(element -> Objects.isNull(element.getCause()))
                .findFirst()
                .orElse(throwable);
    }
    
}
