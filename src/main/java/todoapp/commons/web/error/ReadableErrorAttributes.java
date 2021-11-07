package todoapp.commons.web.error;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 스프링부트에 기본 구현체인 {@link DefaultErrorAttributes}에 message 속성을 덮어쓰기 할 목적으로 작성한 컴포넌트이다.
 *
 * DefaultErrorAttributes는 message 속성을 예외 객체의 값을 사용하기 때문에 사용자가 읽기에 좋은 문구가 아니다.
 * 해당 메시지를 보다 읽기 좋은 문구로 가공해서 제공한다.
 *
 * @author springrunner.kr@gmail.com
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReadableErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {

    private final DefaultErrorAttributes delegate = new DefaultErrorAttributes();
    private final MessageSource messageSource;
    private final Logger log = LoggerFactory.getLogger(ReadableErrorAttributes.class);

    public ReadableErrorAttributes(MessageSource messageSource) {
        this.messageSource = Objects.requireNonNull(messageSource);
    }
    
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> attributes = delegate.getErrorAttributes(webRequest, options);
        Throwable error = getError(webRequest);

        log.debug("errorAttributes: {}, error: {}", attributes, error);

        if (Objects.nonNull(error)) {
            /*
            if (error instanceof TodoEntityNotFoundException) {
                attributes.put("message", "요청한 할 일을 찾을 수 없어요.");   
            } else if (error instanceof MethodArgumentNotValidException) {
                attributes.put("message", "입력 값이 없거나 올바르지 않아요.");
            }
            */
            
            String errorMessage = error.getMessage();
            if (MessageSourceResolvable.class.isAssignableFrom(error.getClass())) {                
                errorMessage = messageSource.getMessage((MessageSourceResolvable) error, webRequest.getLocale());
            } else {
                String errorCode = String.format("Exception.%s", error.getClass().getSimpleName());
                errorMessage = messageSource.getMessage(errorCode, new Object[0], errorMessage, webRequest.getLocale());
            }
            attributes.put("message", errorMessage);
            
            BindingResult bindingResult = extractBindingResult(error);
            if (Objects.nonNull(bindingResult)) {
                List<String> errors = bindingResult
                        .getAllErrors()
                        .stream()
                        .map(oe -> messageSource.getMessage(oe, webRequest.getLocale()))
                        .collect(Collectors.toList());

                attributes.put("errors", errors);
            }
        }

        return attributes;
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return delegate.getError(webRequest);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception error) {
        return delegate.resolveException(request, response, handler, error);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


    /**
     * 예외 개체에서 {@link org.springframework.boot.context.properties.bind.BindResult}를 추출합니다.
     * 없으면 {@literal null}을 반환합니다.
     */
    static BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        if (error instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) error).getBindingResult();
        }
        return null;
    }

}
