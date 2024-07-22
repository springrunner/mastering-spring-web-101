package todoapp.commons.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 스프레드시트 데이터 모델: 행과 열로 구성된 데이터 구조이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class Spreadsheet {

    private final String name;
    private final Row header;
    private final List<Row> rows;

    public Spreadsheet(String name, Row header, List<Row> rows) {
        this.name = name;
        this.header = header;
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public Optional<Row> getHeader() {
        return Optional.ofNullable(header);
    }

    public boolean hasHeader() {
        return Objects.nonNull(header);
    }

    public List<Row> getRows() {
        return rows;
    }

    public boolean hasRows() {
        return Objects.nonNull(rows) && !rows.isEmpty();
    }

    @Override
    public String toString() {
        return "Spreadsheet [name=%s]".formatted(name);
    }

    public static class Row {

        private final List<Cell<?>> cells = new ArrayList<>();

        public static Row of(Object... values) {
            var row = new Row();
            for (Object value : values) {
                row.addCell(value);
            }
            return row;
        }

        public Row addCell(Cell<?> cell) {
            this.cells.add(cell);
            return this;
        }

        public Row addCell(Object cellValue) {
            return this.addCell(new Cell<>(cellValue));
        }

        public List<Cell<?>> getCells() {
            return cells;
        }

    }

    public record Cell<T>(T value) {

    }

}
