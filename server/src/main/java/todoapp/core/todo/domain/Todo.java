package todoapp.core.todo.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 할일 엔티티(Entity)
 *
 * @author springrunner.kr@gmail.com
 */
@Entity(name = "todos")
public class Todo implements Serializable {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private TodoId id;
    private String text;
    private TodoState state = TodoState.ACTIVE;

    private String username;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public Todo(TodoId id, String text, LocalDateTime createdDate) {
        this.id = Objects.requireNonNull(id, "id must be not null");
        this.text = Objects.requireNonNull(text, "text must be not null");
        this.createdDate = createdDate;
        this.lastModifiedDate = createdDate;
    }

    public Todo(TodoId id, String text, String username, LocalDateTime createdDate) {
        this.id = Objects.requireNonNull(id, "id must be not null");
        this.text = Objects.requireNonNull(text, "text must be not null");
        this.username = Objects.requireNonNull(username, "username must be not null");
        this.createdDate = createdDate;
        this.lastModifiedDate = createdDate;
    }

    // for hibernate
    @SuppressWarnings("unused")
    private Todo() {
    }

    public static Todo create(String text, TodoIdGenerator idGenerator) {
        return new Todo(idGenerator.generateId(), text, LocalDateTime.now());
    }

    public static Todo create(String text, String username, TodoIdGenerator idGenerator) {
        return new Todo(idGenerator.generateId(), text, username, LocalDateTime.now());
    }

    public TodoId getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public TodoState getState() {
        return state;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public boolean isCompleted() {
        return state == TodoState.COMPLETED;
    }

    public Todo update(String text, boolean completed) {
        this.text = text;
        this.state = completed ? TodoState.COMPLETED : TodoState.ACTIVE;
        this.lastModifiedDate = LocalDateTime.now();
        return this;
    }

    public Todo update(String text, boolean completed, String username) {
        return verifyUsername(username).update(text, completed);
    }

    private Todo verifyUsername(String username) {
        if (!Objects.equals(username, this.username)) {
            throw new TodoUsernameNotMatchedException();
        }
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(getId(), ((Todo) obj).getId());
    }

    @Override
    public String toString() {
        return "Todo [id=%s, text=%s, state=%s, created-date=%s, last-modified-date=%s]".formatted(id, text, state, createdDate, lastModifiedDate);
    }

    static String checkText(String text) {
        if (Objects.isNull(text) || text.trim().length() < 4) {
            throw new TodoRegistrationException("todo should be at least 4 characters long");
        }
        return text;
    }

    static class Text {

        private final String value;

        public Text(String value) {
            this.value = checkText(value);
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

}
