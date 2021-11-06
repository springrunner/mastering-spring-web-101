package todoapp.security.support;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.security.RolesAllowed;
import java.util.Objects;

/**
 * {@link RolesAllowed} 애노테이션을 편리하게 다루기 위한 지원 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public interface RolesAllowedSupport {

    /**
     * {@link HandlerMethod} 타입에 핸들러에서 {@link RolesAllowed} 애노테이션을 추출한다.
     * 해당 핸들러에 없으면, 핸들러가 등록된 빈(@Controller 또는 @RestController)에서도 찾아본다.
     *
     * @param handler {@link HandlerMethod} 개체
     * @return
     */
    default RolesAllowed getRolesAllowed(Object handler) {
        if (ClassUtils.isAssignableValue(HandlerMethod.class, handler)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RolesAllowed annotation = handlerMethod.getMethodAnnotation(RolesAllowed.class);
            if (Objects.isNull(annotation)) {
                annotation = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), RolesAllowed.class);
            }
            return annotation;
        }
        return null;
    }

}
