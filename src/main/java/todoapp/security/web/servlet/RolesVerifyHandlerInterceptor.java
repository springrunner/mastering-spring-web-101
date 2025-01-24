package todoapp.security.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import todoapp.security.AccessDeniedException;
import todoapp.security.UnauthorizedAccessException;
import todoapp.security.web.RolesAllowedSupport;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        obtainRolesAllowed(handler).ifPresent(rolesAllowed -> {
            log.debug("verify roles-allowed: {}", rolesAllowed);

            // 1. 로그인 되어있나?
            if (Objects.isNull(request.getUserPrincipal())) {
                throw new UnauthorizedAccessException();
            }

            // 2. 역할은 적절한가?
            var matchedRoles = Stream.of(rolesAllowed.value())
                    .filter(request::isUserInRole)
                    .collect(Collectors.toSet());

            log.debug("matched roles: {}", matchedRoles);
            if (matchedRoles.isEmpty()) {
                throw new AccessDeniedException();
            }
        });

        return true;
    }

}
