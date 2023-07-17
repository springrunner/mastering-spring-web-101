package todoapp.commons.web.support;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import todoapp.commons.domain.Spreadsheet;
import todoapp.commons.util.StreamUtils;

/**
 * {@link Spreadsheet} 모델을 보조하기 위한 유틸리티 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface SpreadsheetSupport {

  /**
   * 주어진 모델(model)내에서 {@link Spreadsheet}를 찾아 반환한다.
   *
   * @param model 스프링 MVC 모델
   * @return 스프레드시트 객체
   * @throws IllegalArgumentException 값이 없거나, 두개 이상 발견되면 발생한다
   */
  default Spreadsheet obtainSpreadsheet(Map<String, Object> model) {
    List<Spreadsheet> spreadsheets = model.values().stream().filter(it -> it instanceof Spreadsheet).map(it -> (Spreadsheet) it).collect(Collectors.toList());
    if (spreadsheets.isEmpty()) {
      throw new IllegalArgumentException("spreadsheet object inside the model is required");
    }
    if (spreadsheets.size() > 1) {
      throw new IllegalArgumentException("multiple spreadsheet objects were found");
    }
    return spreadsheets.get(0);
  }

  /**
   * 주어진 셀 목록에 값을 연결해서 하나의 문자열로 만든다. 문자열 연결시 구분 문자를 삽입한다.
   *
   * @param cells 반복자
   * @param delimiter 구분 문자
   * @return 연결된 문자열
   */
  default String joining(Iterable<Spreadsheet.Cell<?>> cells, CharSequence delimiter) {
    return StreamUtils
        .createStreamFromIterator(cells.iterator())
        .map(Spreadsheet.Cell::getValue)
        .map(String::valueOf)
        .collect(Collectors.joining(delimiter));
  }

}
