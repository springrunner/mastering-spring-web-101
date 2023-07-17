package todoapp.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todoapp.core.shared.identifier.UserId;
import todoapp.core.user.domain.User;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserSessionTest {

    @Test
    @DisplayName("생성 시 전달되는 사용자 객체가 `null`이면 NPE가 발생한다")
    void when_NullUser_Expect_NullPointerException() {
        assertThatThrownBy(() -> new UserSession(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("user object must be not null");
    }

    @Test
    @DisplayName("생성 시 ROLE_USER 역할은 기본으로 추가된다")
    void when_Created_Expect_DefaultRoleUser() {
        var userSession = new UserSession(createUser("tester"));

        var roles = userSession.getRoles();

        assertThat(roles).isNotNull();
        assertThat(roles).contains(UserSession.ROLE_USER);
    }

    @Test
    @DisplayName("getUser() 호출 시 전달된 사용자 객체가 그대로 반환된다")
    void when_GetUserCalled_Expect_ReturnSameUser() {
        var user = createUser("tester");
        var userSession = new UserSession(user);

        var retrievedUser = userSession.getUser();

        assertThat(retrievedUser).isSameAs(user);
        assertThat(retrievedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("역할 확인(hasRole)시 세션에 존재하는 역할은 참(true), 존재하지 않는 역할은 거짓(false)을 반환한다")
    void when_HasRoleCalled_Expect_TrueForExistingRoleAndFalseForNotAddedRole() {
        var userSession = new UserSession(createUser("tester"));

        assertThat(userSession.hasRole(UserSession.ROLE_USER)).isTrue();
        assertThat(userSession.hasRole("ROLE_ADMIN")).isFalse();
    }

    User createUser(String username) {
        return new User(UserId.of(UUID.randomUUID().toString()), username, "password");
    }

}
