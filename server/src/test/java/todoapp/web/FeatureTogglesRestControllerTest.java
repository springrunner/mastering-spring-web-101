package todoapp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import todoapp.web.model.FeatureTogglesProperties;
import todoapp.web.model.SiteProperties;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeatureTogglesRestController.class)
class FeatureTogglesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureTogglesProperties featureTogglesProperties;

    @MockBean
    private SiteProperties siteProperties;

    @Test
    void featureToggles() throws Exception {
        when(featureTogglesProperties.auth()).thenReturn(true);
        when(featureTogglesProperties.onlineUsersCounter()).thenReturn(false);

        mockMvc.perform(get("/api/feature-toggles"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"auth\":true,\"onlineUsersCounter\":false}"));
    }
}