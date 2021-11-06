package todoapp.security.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

import java.util.Objects;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

/**
 * {@link javax.servlet.http.HttpSession}을 사용자 세션 저장소로 사용하는 구현체
 *
 * @author springrunner.kr@gmail.com
 */
@Component
public class HttpUserSessionRepository implements UserSessionRepository {

    private static final String USER_SESSION_KEY = HttpUserSessionRepository.class.getName();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserSession get() {
        return (UserSession) currentRequestAttributes().getAttribute(USER_SESSION_KEY, SCOPE_SESSION);
    }

    @Override
    public void set(UserSession session) {
        Objects.requireNonNull(session, "session object must be not null");
        currentRequestAttributes().setAttribute(USER_SESSION_KEY, session, SCOPE_SESSION);
        log.info("saved new session: {}", session);
    }

    @Override
    public void clear() {
        currentRequestAttributes().removeAttribute(USER_SESSION_KEY, SCOPE_SESSION);
        log.info("session cleared!");
    }

    private RequestAttributes currentRequestAttributes() {
        return Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
    }

}