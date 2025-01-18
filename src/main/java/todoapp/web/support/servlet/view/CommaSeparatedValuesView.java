package todoapp.web.support.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.core.shared.util.Spreadsheet;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * {@link Spreadsheet} 모델을 CSV(comma-separated values) 파일 형식으로 출력하는 뷰 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class CommaSeparatedValuesView extends AbstractView {

    private static final String CONTENT_TYPE = "text/csv";
    private static final String FILE_EXTENSION = "csv";

    private final Logger log = LoggerFactory.getLogger(getClass());

    public CommaSeparatedValuesView() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        var spreadsheet = Spreadsheet.obtainSpreadsheet(model);
        log.info("write spreadsheet content to csv file: {}", spreadsheet);

        var encodedName = URLEncoder.encode(spreadsheet.getName(), StandardCharsets.UTF_8);
        var contentDisposition = "attachment; filename=\"%s.%s\"".formatted(encodedName, FILE_EXTENSION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        if (spreadsheet.hasHeader()) {
            var header = spreadsheet.getHeader().map(row -> row.joining(",")).orElse("");
            response.getWriter().println(header);
        }

        if (spreadsheet.hasRows()) {
            for (var row : spreadsheet.getRows()) {
                response.getWriter().println(row.joining(","));
            }
        }

        response.flushBuffer();
    }

}
