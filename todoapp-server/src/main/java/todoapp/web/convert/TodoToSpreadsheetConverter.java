package todoapp.web.convert;

import todoapp.commons.domain.Spreadsheet;
import todoapp.commons.util.StreamUtils;
import todoapp.core.todo.domain.Todo;

/**
 * {@link Todo} 모델을 {@link Spreadsheet} 모델로 변환하는 변환기이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoToSpreadsheetConverter {

    public Spreadsheet convert(Iterable<Todo> todos) {
        var header = Spreadsheet.Row.of("id", "text", "completed");

        var todoStream = StreamUtils.createStreamFromIterator(todos.iterator());
        var rows = todoStream.map(this::mapRow).toList();

        return new Spreadsheet("todos", header, rows);
    }

    private Spreadsheet.Row mapRow(Todo todo) {
        return Spreadsheet.Row.of(todo.getId(), todo.getText(), todo.isCompleted() ? "완료" : "미완료");
    }

}
