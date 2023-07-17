package todoapp.core.shared.identifier;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 사용자 식별자(identifier)
 *
 * @author springrunner.kr@gmail.com
 */
@Embeddable
public class UserId implements Serializable {

    private String value;

    UserId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("user-id is must not be null or empty");
        }
        this.value = value;
    }

    // for hibernate
    @SuppressWarnings("unused")
    private UserId() {
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(value, ((UserId) obj).value);
    }

    @Override
    public String toString() {
        return value;
    }

}
