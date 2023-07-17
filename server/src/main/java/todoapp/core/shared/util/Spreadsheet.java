package todoapp.core.shared.util;

import todoapp.core.foundation.util.StreamUtils;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 주어진 맵(map) 객체에서 {@link Spreadsheet} 객체를 찾아 반환한다.
     *
     * @param map 맵 객체
     * @return 스프레드시트 객체
     * @throws IllegalArgumentException 값이 없거나, 두개 이상 발견되면 발생한다
     */
    public static Spreadsheet obtainSpreadsheet(Map<String, Object> map) {
        var spreadsheets = map.values().stream().filter(it -> it instanceof Spreadsheet).toList();
        if (spreadsheets.isEmpty()) {
            throw new IllegalArgumentException("spreadsheet object inside the map is required");
        }
        if (spreadsheets.size() > 1) {
            throw new IllegalArgumentException("multiple spreadsheet objects were found");
        }
        return (Spreadsheet) spreadsheets.getFirst();
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

        public String joining(CharSequence delimiter) {
            return StreamUtils
                    .createStreamFromIterator(cells.iterator())
                    .map(Spreadsheet.Cell::value)
                    .map(String::valueOf)
                    .collect(Collectors.joining(delimiter));
        }

    }

    public record Cell<T>(T value) {

    }

}
