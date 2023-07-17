package todoapp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todoapp.core.shared.identifier.TodoId;
import todoapp.core.shared.identifier.UserId;
import todoapp.core.todo.domain.Todo;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryTodoRepositoryTest {

    private InMemoryTodoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTodoRepository();
    }

    @Test
    @DisplayName("저장된 할일(Todo)이 없을 때 findAll() 호출 시 빈 목록을 반환한다")
    void when_NoTodosSaved_Expect_findAllReturnsEmptyList() {
        var todos = repository.findAll();

        assertThat(todos).isNotNull();
        assertThat(todos).isEmpty();
    }

    @Test
    @DisplayName("할일(Todo) 하나를 저장 후 findAll() 호출 시 해당 할일이 목록에 존재한다")
    void when_OneTodoSaved_Expect_findAllReturnsListWithSavedTodo() {
        var todo = createTodo("tester");
        repository.save(todo);

        var todos = repository.findAll();

        assertThat(todos).hasSize(1);
        assertThat(todos).containsExactly(todo);
    }

    @Test
    @DisplayName("findByOwner() 호출 시 해당 소유자(UserId)의 할일(Todo)만 반환한다")
    void when_TodosWithDifferentOwnersSaved_Expect_findByOwnerReturnsOnlyMatchingTodos() {
        var todo1 = createTodo("tester");
        var todo2 = createTodo("springrunner");
        var todo3 = createTodo("tester");

        repository.save(todo1);
        repository.save(todo2);
        repository.save(todo3);

        var foundForTester = repository.findByOwner(UserId.of("tester"));
        var foundForSpringRunner = repository.findByOwner(UserId.of("springrunner"));

        assertThat(foundForTester).containsExactlyInAnyOrder(todo1, todo3);
        assertThat(foundForSpringRunner).containsExactly(todo2);
    }

    @Test
    @DisplayName("저장되지 않는 TodoId로 findById()를 호출하면 빈 `Optional`을 반환한다")
    void when_FindByIdWithNonExistentId_Expect_EmptyOptional() {
        var result = repository.findById(TodoId.of("non-existent-id"));

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("저장된 TodoId로 findById()를 호출하면 해당 할일(Todo)을 `Optional`로 감싸 반환한다")
    void when_FindByIdWithExistingId_Expect_ReturnOptionalWithTodo() {
        var todo = createTodo("tester");
        repository.save(todo);

        var result = repository.findById(todo.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(todo);
    }

    @Test
    @DisplayName("새로운 할일(Todo)을 save() 하면 목록에 추가된다")
    void when_SaveNewTodo_Expect_AddedToList() {
        var todo = createTodo("tester");

        var returnedTodo = repository.save(todo);

        assertThat(returnedTodo).isSameAs(todo);
        assertThat(repository.findAll()).contains(todo);
    }

    @Test
    @DisplayName("이미 존재하는 할일(Todo)을 save() 하면 중복 추가되지 않는다 (동일 객체 여부 확인)")
    void when_SaveExistingTodo_Expect_NoDuplicateInList() {
        var todo = createTodo("tester");
        repository.save(todo);

        repository.save(todo);
        var todos = repository.findAll();

        assertThat(todos).hasSize(1);
        assertThat(todos.getFirst()).isEqualTo(todo);
    }

    @Test
    @DisplayName("delete()로 삭제 요청 시 목록에서 해당 할일(Todo)이 제거된다")
    void when_DeleteExistingTodo_Expect_RemovedFromList() {
        var todo = createTodo("tester");
        repository.save(todo);

        repository.delete(todo);

        assertThat(repository.findAll()).doesNotContain(todo);
    }

    Todo createTodo(String owner) {
        return new Todo(
            TodoId.of(UUID.randomUUID().toString()),
            "Task One",
            UserId.of(owner),
            LocalDateTime.now()
        );
    }

}
