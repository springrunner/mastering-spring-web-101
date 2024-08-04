package todoapp.web.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 기능 토글 모델
 *
 * @author springrunner.kr@gmail.com
 */
@ConfigurationProperties("todoapp.feature-toggles")
public record FeatureTogglesProperties(boolean auth, boolean onlineUsersCounter) {

}
