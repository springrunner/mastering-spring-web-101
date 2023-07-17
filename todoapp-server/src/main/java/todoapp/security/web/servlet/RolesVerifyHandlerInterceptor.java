package todoapp.security.web.servlet;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todoapp.commons.NotImplementedException;
import todoapp.security.UserSessionRepository;
import todoapp.security.support.RolesAllowedSupport;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {

  private final UserSessionRepository sessionRepository;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public RolesVerifyHandlerInterceptor(UserSessionRepository sessionRepository) {
    this.sessionRepository = Objects.requireNonNull(sessionRepository);
  }

  @Override
  public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    throw new NotImplementedException();
  }

}
