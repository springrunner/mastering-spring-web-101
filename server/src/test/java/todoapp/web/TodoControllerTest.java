package todoapp.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import todoapp.commons.domain.Spreadsheet;
import todoapp.commons.web.view.CommaSeparatedValuesView;
import todoapp.core.todo.TodoFixture;
import todoapp.core.todo.application.TodoFind;
import todoapp.web.convert.TodoToSpreadsheetConverter;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author springrunner.kr@gmail.com
 */
@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Mock
    private TodoFind todoFind;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        var contentNegotiationManager = new ContentNegotiationManager(new HeaderContentNegotiationStrategy());
        var contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);
        contentNegotiatingViewResolver.setDefaultViews(List.of(new CommaSeparatedValuesView()));

        var characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new TodoController(todoFind))
                .setViewResolvers(contentNegotiatingViewResolver)
                .addFilter(characterEncodingFilter)
                .build();
    }

    @Test
    void downloadTodos_ShouldReturnCsv() throws Exception {
        var todos = List.of(
                TodoFixture.random(),
                TodoFixture.random()
        );

        when(todoFind.all()).thenReturn(todos);

        mockMvc.perform(get("/todos").accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string(toCSV(new TodoToSpreadsheetConverter().convert(todos))));
    }

    private String toCSV(Spreadsheet spreadsheet) {
        var header = spreadsheet.getHeader()
                .map(row ->
                        row.getCells().stream()
                                .map(cell -> cell.value().toString())
                                .collect(Collectors.joining(",")))
                .orElse("");

        var rows = spreadsheet.getRows().stream()
                .map(row ->
                        row.getCells().stream()
                                .map(cell -> cell.value().toString())
                                .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));

        return header + "\n" + rows + "\n";
    }

}
