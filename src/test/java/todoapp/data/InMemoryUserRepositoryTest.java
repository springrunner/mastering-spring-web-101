package todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todoapp.core.foundation.crypto.PasswordEncoder;
import todoapp.core.shared.identifier.UserId;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserIdGenerator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryUserRepositoryTest {

    private final UserIdGenerator userIdGenerator = () -> UserId.of(UUID.randomUUID().toString());
    private final PasswordEncoder passwordEncoder = (password) -> password;

    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository(userIdGenerator, passwordEncoder);
    }

    @Test
    @DisplayName("생성시 필요한 의존성 중 하나라도 `null`이면 NPE가 발생한다")
    void when_ConstructorHasNullParams_Expect_NullPointerException() {
        assertThatThrownBy(() -> new InMemoryUserRepository(null, passwordEncoder))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new InMemoryUserRepository(userIdGenerator, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("저장되지 않은 사용자 이름으로 findByUsername()을 호출하면 빈 `Optional`을 반환한다")
    void when_FindByUsernameWithUnknownUser_Expect_EmptyOptional() {
        var result = repository.findByUsername("unknown-user");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("저장된 사용자 이름으로 findByUsername()을 호출하면 해당 사용자(User)를 `Optional`로 감싸 반환한다")
    void when_FindByUsernameWithKnownUser_Expect_ReturnOptionalWithUser() {
        var user = createUser("tester");
        repository.save(user);

        var result = repository.findByUsername(user.getUsername());

        assertThat(result).isPresent();
        assertThat(result.get()).isSameAs(user);
    }

    @Test
    @DisplayName("새로운 사용자(User)를 save() 하면 목록에 추가된다")
    void when_SaveNewUser_Expect_AddedToRepository() {
        var user = createUser("tester");

        var returnedUser = repository.save(user);
        assertThat(returnedUser).isSameAs(user);

        var found = repository.findByUsername(user.getUsername());
        assertThat(found).isPresent();
        assertThat(found.get()).isSameAs(user);
    }

    User createUser(String username) {
        return new User(UserId.of(UUID.randomUUID().toString()), username, "password");
    }

}