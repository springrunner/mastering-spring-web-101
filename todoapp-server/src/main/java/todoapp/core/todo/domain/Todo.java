package todoapp.core.todo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

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
  private String title;
  private TodoState state = TodoState.ACTIVE;

  private String username;

  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public Todo(TodoId id, String title, LocalDateTime createdDate) {
    this.id = Objects.requireNonNull(id, "id must be not null");
    this.title = Objects.requireNonNull(title, "title must be not null");
    this.createdDate = createdDate;
    this.lastModifiedDate = createdDate;
  }

  public Todo(TodoId id, String title, String username, LocalDateTime createdDate) {
    this.id = Objects.requireNonNull(id, "id must be not null");
    this.title = Objects.requireNonNull(title, "title must be not null");
    this.username = Objects.requireNonNull(username, "username must be not null");
    this.createdDate = createdDate;
    this.lastModifiedDate = createdDate;
  }

  public TodoId getId() {
    return id;
  }

  public String getTitle() {
    return title;
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

  public Todo update(String title, boolean completed) {
    this.title = title;
    this.state = completed ? TodoState.COMPLETED : TodoState.ACTIVE;
    this.lastModifiedDate = LocalDateTime.now();
    return this;
  }

  public Todo update(String title, boolean completed, String username) {
    return verifyUsername(username).update(title, completed);
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
    return "Todo [id=%s, title=%s, state=%s, created-date=%s, last-modified-date=%s]".formatted(id, title, state, createdDate, lastModifiedDate);
  }

  // for hibernate
  @SuppressWarnings("unused")
  private Todo() {}

  public static Todo create(String title, TodoIdGenerator idGenerator) {
    return new Todo(idGenerator.generateId(), title, LocalDateTime.now());
  }

  public static Todo create(String title, String username, TodoIdGenerator idGenerator) {
    return new Todo(idGenerator.generateId(), title, username, LocalDateTime.now());
  }

  class Title {

    private final String value;

    public Title(String value) {
      this.value = checkTitle(value);
    }

    public String getValue() {
      return value;
    }

    static String checkTitle(String title) {
      if (Objects.isNull(title) || title.trim().length() < 4) {
        throw new TodoRegistrationException("todo must be minimum of 4 characters");
      }
      return title;
    }

  }

}
