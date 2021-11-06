package todoapp.web.convert;

import todoapp.commons.domain.Spreadsheet;
import todoapp.commons.util.StreamUtils;
import todoapp.core.todos.domain.Todo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link Todo} 모델을 {@link Spreadsheet} 모델로 변환하는 변환기
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoToSpreadsheetConverter {

    public Spreadsheet convert(Iterable<Todo> todos) {
        Spreadsheet.Row header = new Spreadsheet.Row()
                                                .addCell("id")
                                                .addCell("title")
                                                .addCell("completed");

        Stream<Todo> todoStream = StreamUtils.createStreamFromIterator(todos.iterator());
        List<Spreadsheet.Row> rows = todoStream.map(this::mapRow).collect(Collectors.toList());

        return new Spreadsheet("todos", header, rows);
    }

    private Spreadsheet.Row mapRow(Todo todo) {
        return new Spreadsheet.Row()
                              .addCell(todo.getId())
                              .addCell(todo.getTitle())
                              .addCell(todo.isCompleted() ? "완료" : "미완료");
    }

}
