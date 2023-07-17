package todoapp.security.web.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import todoapp.core.foundation.NotImplementedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

/**
 * {@link HttpServletRequest} 객체로 로그인 된 사용자 세션({@link UserSession}에 접근 할 수 있도록 지원하는 필터 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class UserSessionFilter extends OncePerRequestFilter {

    private final UserSessionHolder userSessionHolder;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserSessionFilter(UserSessionHolder userSessionHolder) {
        this.userSessionHolder = Objects.requireNonNull(userSessionHolder);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("processing user-session filter");

        throw new NotImplementedException();
    }

    /**
     * 로그인 사용자 세션을 기반으로 인증 객체와 역할 확인 기능을 제공한다.
     */
    final static class UserSessionRequestWrapper extends HttpServletRequestWrapper {

        final UserSession userSession;

        private UserSessionRequestWrapper(HttpServletRequest request, UserSession userSession) {
            super(request);
            this.userSession = userSession;
        }

        @Override
        public Principal getUserPrincipal() {
            throw new NotImplementedException();
        }

        @Override
        public boolean isUserInRole(String role) {
            throw new NotImplementedException();
        }

    }

}
