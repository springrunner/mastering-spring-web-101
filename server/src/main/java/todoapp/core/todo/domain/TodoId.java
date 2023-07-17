package todoapp.core.todo.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 할일 일련번호(identity)
 *
 * @author springrunner.kr@gmail.com
 */
@Embeddable
public class TodoId implements Serializable {

    private String value;

    public TodoId(String value) {
        if (value == null || value.isBlank()) {
            throw new TodoRegistrationException("todo-id is must not be null or empty");
        }
        this.value = value;
    }

    // for hibernate
    @SuppressWarnings("unused")
    private TodoId() {
    }

    public static TodoId of(String value) {
        return new TodoId(value);
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
        return Objects.equals(value, ((TodoId) obj).value);
    }

    @Override
    public String toString() {
        return value;
    }

}
