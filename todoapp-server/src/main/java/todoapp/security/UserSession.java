package todoapp.security;

import todoapp.core.user.domain.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 사용자 세션 모델
 *
 * @author springrunner.kr@gmail.com
 */
public class UserSession {

    public static final String ROLE_USER = "ROLE_USER";

    private final User user;
    private final Set<String> roles = new HashSet<>();

    public UserSession(User user) {
        this.user = Objects.requireNonNull(user, "user object must be not null");
        this.roles.add(ROLE_USER);
    }

    public String getName() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    public Set<String> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserSession {");
        sb.append("user=").append(user);
        sb.append(", roles=").append(roles);
        sb.append(" }");
        return sb.toString();
    }

}
