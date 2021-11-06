package todoapp.commons.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 스프레드시트 모델: 행과 열로 구성된 데이터 구조
 *
 * @author springrunner.kr@gmail.com
 */
public class Spreadsheet {

	private String name;
	private Optional<Row> header;
	private List<Row> rows;

	public Spreadsheet(String name, Row header, List<Row> rows) {
		this.name = name;
		this.header = Optional.ofNullable(header);
		this.rows = rows;
	}

	public String getName() {
		return name;
	}

	public Optional<Row> getHeader() {
		return header;
	}

    public boolean hasHeader() {
        return header.isPresent();
    }

	public List<Row> getRows() {
		return rows;
	}

    public boolean hasRows() {
        return Objects.nonNull(rows) && !rows.isEmpty();
    }


	public static class Row {
	
		private List<Cell<?>> cells = new ArrayList<>();
		
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
	
	public static class Cell<T> {
		
		private T value;

		public Cell(T value) {
			this.value = value;
		}

		public T getValue() {
			return value;
		}
		
	}
	
}
