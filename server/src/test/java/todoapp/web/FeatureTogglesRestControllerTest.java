package todoapp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSessionHolder;
import todoapp.web.model.FeatureTogglesProperties;
import todoapp.web.model.SiteProperties;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author springrunner.kr@gmail.com
 */
@WebMvcTest(FeatureTogglesRestController.class)
class FeatureTogglesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FeatureTogglesProperties featureTogglesProperties;

    @MockitoBean
    private SiteProperties siteProperties;

    @MockitoBean
    private UserSessionHolder userSessionHolder;

    @MockitoBean
    private ProfilePictureStorage profilePictureStorage;

    @Test
    void featureToggles() throws Exception {
        when(featureTogglesProperties.auth()).thenReturn(true);
        when(featureTogglesProperties.onlineUsersCounter()).thenReturn(false);

        mockMvc.perform(get("/api/feature-toggles"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"auth\":true,\"onlineUsersCounter\":false}"));
    }

}
