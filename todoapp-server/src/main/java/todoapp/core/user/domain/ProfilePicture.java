package todoapp.core.user.domain;

import java.net.URI;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 * 사용자 프로필 이미지 값 객체(Value Object)이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Embeddable
public class ProfilePicture {

  private URI uri;

  public ProfilePicture(URI uri) {
    setUri(Objects.requireNonNull(uri));
  }

  public URI getUri() {
    return uri;
  }

  private void setUri(URI uri) {
    this.uri = uri;
  }

  @Override
  public int hashCode() {
    return Objects.hash(uri);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    ProfilePicture other = (ProfilePicture) obj;
    return Objects.equals(uri, other.uri);
  }

  @Override
  public String toString() {
    return uri.toString();
  }

  // for hibernate
  @SuppressWarnings("unused")
  private ProfilePicture() {}

}
