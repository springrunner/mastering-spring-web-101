package todoapp.web.model;

/**
 * 사이트 정보 모델
 *
 * @author springrunner.kr@gmail.com
 */
public record SiteProperties(String author, String description) {

  public static SiteProperties create(String author) {
    return new SiteProperties(author, "Keep track of your todos!");
  }
}
