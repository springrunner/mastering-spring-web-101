package todoapp.commons.context;

import java.util.Locale;

/**
 * 예외를 메시지로 번역하는 역할을 수행하는 컴포넌트 인터페이스이다.
 * 번역할 때 로케일을 기반으로 메시지를 번역한다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface ExceptionMessageTranslator {

    /**
     * 입력된 예외에 대해 메시지를 작성해서 반환한다.
     * 기본 메시지(defaultMessage)로 예외 개체 내부에 메시지를 사용한다.
     *
     * @param throwable 예외 개체
     * @param locale 언어/국가
     * @return
     */
    String getMessage(Throwable throwable, Locale locale);

    /**
     * 입력된 예외에 대해 메시지를 작성해서 반환한다.
     * 적절한 메시지를 찾지 못하면 기본 메시지를 반환한다.
     *
     * @param throwable 예외 개체
     * @param defaultMessage 기본 메시지
     * @param locale 언어/국가
     * @return
     */
    String getMessage(Throwable throwable, String defaultMessage, Locale locale);

}
