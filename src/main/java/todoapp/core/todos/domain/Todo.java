package todoapp.core.todos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 할 일 엔티티(Entity)
 *
 * @author springrunner.kr@gmail.com
 */
@Entity
public class Todo {

    @Id
    private Long id;
    private String title;
    private TodoState state = TodoState.ACTIVE;

    private String username;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public TodoState getState() {
        return state;
    }

    private void setState(TodoState state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    private void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    private void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isCompleted() {
        return state == TodoState.COMPLETED;
    }

    public Todo update(String title, boolean completed) {
        setTitle(title);
        setState(completed ? TodoState.COMPLETED : TodoState.ACTIVE);
        setLastModifiedDate(LocalDateTime.now());
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(getId(), todo.getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Todo{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }


    public static Todo create(String title) {
        Todo todo = new Todo();
        todo.setId(System.nanoTime());
        todo.setTitle(title);
        todo.setCreatedDate(LocalDateTime.now());
        todo.setLastModifiedDate(todo.getCreatedDate());
        return todo;
    }

    public static Todo create(String title, String username) {
        Todo todo = new Todo();
        todo.setId(System.nanoTime());
        todo.setTitle(title);
        todo.setUsername(username);
        todo.setCreatedDate(LocalDateTime.now());
        todo.setLastModifiedDate(todo.getCreatedDate());
        return todo;
    }

}
