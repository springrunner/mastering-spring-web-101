package todoapp.web.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 사이트 정보 모델
 *
 * @author springrunner.kr@gmail.com
 */
@ConfigurationProperties("todoapp.site")
public record SiteProperties(String author, String description) {

}
