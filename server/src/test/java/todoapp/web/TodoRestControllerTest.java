package todoapp.web;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import todoapp.core.todo.TodoFixture;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.application.TodoRegistry;
import todoapp.core.todo.converter.json.TodoModule;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author springrunner.kr@gmail.com
 */
@ExtendWith(MockitoExtension.class)
class TodoRestControllerTest {

    @Mock
    private TodoFind todoFind;

    @Mock
    private TodoRegistry todoRegistry;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        var objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new TodoModule(), new JavaTimeModule())
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(new TodoRestController(todoFind, todoRegistry))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void readAll_ShouldReturnAllTodos() throws Exception {
        var first = TodoFixture.random();
        var second = TodoFixture.random();

        given(todoFind.all()).willReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(first.getId().toString()))
                .andExpect(jsonPath("$[0].text").value(first.getText()))
                .andExpect(jsonPath("$[1].id").value(second.getId().toString()))
                .andExpect(jsonPath("$[1].text").value(second.getText()));
    }

    @Test
    void create_ShouldRegisterTodo() throws Exception {
        var todoText = "New Task";
        var todoJson = "{\"text\":\"" + todoText + "\"}";

        mockMvc.perform(
                post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoJson)
        ).andExpect(status().isCreated());

        verify(todoRegistry).register(todoText);
    }

    @Test
    void create_ShouldReturnBadRequest_WhenTitleIsInvalid() throws Exception {
        var invalidTodoJson = "{\"text\":\"abc\"}";

        mockMvc.perform(
                post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTodoJson)
        ).andExpect(status().isBadRequest());
    }

}
