package todoapp.security.web.servlet;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import todoapp.commons.NotImplementedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

/**
 * HttpServletRequest가 로그인 사용자 세션({@link UserSession}을 사용 할 수 있도록 지원하는 필터 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class UserSessionFilter extends OncePerRequestFilter {

  private final UserSessionRepository userSessionRepository;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public UserSessionFilter(UserSessionRepository userSessionRepository) {
    this.userSessionRepository = Objects.requireNonNull(userSessionRepository);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("Processing user-session filter");

    throw new NotImplementedException();
  }


  /**
   * 로그인 사용자 세션을 기반으로 인증 객체와 역할 확인 기능을 제공한다.
   *
   * @author springrunner.kr@gmail.com
   */
  final class UserSessionRequestWrapper extends HttpServletRequestWrapper {

    final Optional<UserSession> userSession;

    protected UserSessionRequestWrapper(HttpServletRequest request, UserSession userSession) {
      super(request);
      this.userSession = Optional.ofNullable(userSession);
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
