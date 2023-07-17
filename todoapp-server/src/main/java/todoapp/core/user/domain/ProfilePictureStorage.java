package todoapp.core.user.domain;

import java.net.URI;
import org.springframework.core.io.Resource;

/**
 * 프로필 이미지 보관소 인터페이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface ProfilePictureStorage {

  /**
   * 프로필 이미지를 저장 후 URI 생성해서 반환한다.
   *
   * @param resource 이미지 자원
   * @return 프로필 이미지에 접근 가능한 URI 객체
   */
  URI save(Resource resource);

  /**
   * URI가 가르키는 자원을 찾아 반환한다.
   *
   * @param uri URI 객체
   * @return 자원 객체
   */
  Resource load(URI uri);

}
