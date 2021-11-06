package todoapp.commons.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.commons.domain.Spreadsheet;
import todoapp.commons.web.support.SpreadsheetSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * {@link Spreadsheet} 모델을 CSV(comma-separated values) 파일 형식으로 출력하는 뷰 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class CommaSeparatedValuesView extends AbstractView implements SpreadsheetSupport {

    private static final String CONTENT_TYPE = "text/csv";
    private static final String FILE_EXTENSION = "csv";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public CommaSeparatedValuesView() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Spreadsheet spreadsheet = obtainSpreadsheet(model);
        log.info("write spreadsheet content to csv file: {}", spreadsheet);

        String encodedName = URLEncoder.encode(spreadsheet.getName(), "UTF-8");
        String contentDisposition = String.format("attachment; filename=\"%s.%s\"", encodedName, FILE_EXTENSION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        if (spreadsheet.hasHeader()) {
            String header = spreadsheet.getHeader().map(row -> joining(row.getCells(), ",")).orElse("");
            response.getWriter().println(header);
        }

        if (spreadsheet.hasRows()) {
            for(Spreadsheet.Row row : spreadsheet.getRows()) {
                String line = joining(row.getCells(), ",");
                response.getWriter().println(line);
            }
        }

        response.flushBuffer();
    }

}
