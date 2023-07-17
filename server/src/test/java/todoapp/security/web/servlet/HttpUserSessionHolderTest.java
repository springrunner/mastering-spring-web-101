package todoapp.security.web.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import todoapp.core.shared.identifier.UserId;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpUserSessionHolderTest {

    private HttpUserSessionHolder userSessionHolder;
    private MockHttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        userSessionHolder = new HttpUserSessionHolder();
        mockHttpSession = new MockHttpSession();

        var request = new MockHttpServletRequest();
        request.setSession(mockHttpSession);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    @DisplayName("`RequestContextHolder`가 비어있을 때 사용자 세션을 취득(get)하면 NPE가 발생한다")
    void when_RequestContextHolderIsEmpty_Expect_NullPointerExceptionOnGet() {
        RequestContextHolder.resetRequestAttributes();

        assertThatThrownBy(() -> userSessionHolder.get())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("HTTP 세션에 사용자 세션이 존재할 때 취득(get)하면 해당 세션을 반환한다")
    void when_HttpSessionHasUserSession_Expect_UserSessionRetrievedByGet() {
        var userSession = newTestUserSession();
        mockHttpSession.setAttribute(HttpUserSessionHolder.USER_SESSION_KEY, userSession);

        var retrieved = userSessionHolder.get();

        assertThat(retrieved).isNotNull();
        assertThat(retrieved).isSameAs(userSession);
    }

    @Test
    @DisplayName("`null`을 설정(set)하면 NPE가 발생한다")
    void when_NullSet_Expect_NullPointerException() {
        assertThatThrownBy(() -> userSessionHolder.set(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("session object must be not null");
    }

    @Test
    @DisplayName("사용자 세션을 설정(set)하면 HTTP 세션에 저장한다")
    void when_UserSessionSet_Expect_HttpSessionStored() {
        var userSession = newTestUserSession();

        userSessionHolder.set(userSession);

        var retrieved = mockHttpSession.getAttribute(HttpUserSessionHolder.USER_SESSION_KEY);
        assertThat(retrieved).isSameAs(userSession);
    }

    @Test
    @DisplayName("사용자 세션을 초기화(reset)하면 HTTP 세션이 비워진다")
    void when_ResetUserSession_Expect_HttpSessionCleared() {
        mockHttpSession.setAttribute(HttpUserSessionHolder.USER_SESSION_KEY, newTestUserSession());

        userSessionHolder.reset();

        var retrieved = mockHttpSession.getAttribute(HttpUserSessionHolder.USER_SESSION_KEY);
        assertThat(retrieved).isNull();
    }

    UserSession newTestUserSession() {
        return new UserSession(new User(UserId.of(UUID.randomUUID().toString()), "tester", "password"));
    }

}
