package todoapp.security.web;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;
import java.util.Optional;

/**
 * {@link RolesAllowed} 애노테이션을 편리하게 다루기 위한 유틸리티 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface RolesAllowedSupport {

    /**
     * {@link HandlerMethod} 타입에 핸들러에서 {@link RolesAllowed} 애노테이션을 추출한다. 해당 핸들러에 없으면, 핸들러가
     * 등록된 @Controller 또는 @RestController 에서도 찾아본다.
     *
     * @param handler 핸들러({@link HandlerMethod}) 객체
     * @return {@link RolesAllowed} 객체
     */
    default Optional<RolesAllowed> obtainRolesAllowed(Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            var annotation = handlerMethod.getMethodAnnotation(RolesAllowed.class);
            if (Objects.isNull(annotation)) {
                annotation = AnnotatedElementUtils.findMergedAnnotation(
                    handlerMethod.getBeanType(), RolesAllowed.class
                );
            }
            return Optional.ofNullable(annotation);
        }
        return Optional.empty();
    }

}
