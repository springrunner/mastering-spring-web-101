package todoapp.core.todo.domain;

import jakarta.persistence.*;
import todoapp.core.shared.identifier.TodoId;
import todoapp.core.shared.identifier.UserId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 할일 도메인 모델
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

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "owner_id"))
    private UserId owner;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public Todo(TodoId id, String text, LocalDateTime createdDate) {
        this.id = Objects.requireNonNull(id, "id must be not null");
        this.text = Objects.requireNonNull(text, "text must be not null");
        this.createdDate = createdDate;
        this.lastModifiedDate = createdDate;
    }

    public Todo(TodoId id, String text, UserId owner, LocalDateTime createdDate) {
        this.id = Objects.requireNonNull(id, "id must be not null");
        this.text = Objects.requireNonNull(text, "text must be not null");
        this.owner = Objects.requireNonNull(owner, "owner must be not null");
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

    public static Todo create(String text, UserId owner, TodoIdGenerator idGenerator) {
        return new Todo(idGenerator.generateId(), text, owner, LocalDateTime.now());
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

    public UserId getOwner() {
        return owner;
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

    public Todo edit(String text, boolean completed) {
        this.text = text;
        this.state = completed ? TodoState.COMPLETED : TodoState.ACTIVE;
        this.lastModifiedDate = LocalDateTime.now();
        return this;
    }

    public Todo edit(String text, boolean completed, UserId owner) {
        if (!Objects.equals(owner, getOwner())) {
            throw new TodoOwnerMismatchException();
        }
        return edit(text, completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object todo) {
        if (this == todo)
            return true;
        if (todo == null || getClass() != todo.getClass())
            return false;
        return Objects.equals(getId(), ((Todo) todo).getId());
    }

    @Override
    public String toString() {
        return "Todo [id=%s, text=%s, state=%s, created-date=%s, last-modified-date=%s]".formatted(id, text, state, createdDate, lastModifiedDate);
    }

    static String checkText(String text) {
        if (Objects.isNull(text) || text.trim().length() < 4) {
            throw new TodoRegistrationRejectedException("text should be at least 4 characters long");
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
