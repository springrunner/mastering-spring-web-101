package todoapp.core.user.domain;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * 사용자 엔티티(Entity)
 *
 * @author springrunner.kr@gmail.com
 */
@Entity(name = "users")
public class User {

  @Id
  private String username;
  private String password;
  private ProfilePicture profilePicture;

  public User(String username, String password) {
    setUsername(username);
    setPassword(password);
  }

  public String getUsername() {
    return username;
  }

  private void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  private void setPassword(String password) {
    this.password = password;
  }

  public ProfilePicture getProfilePicture() {
    return profilePicture;
  }

  public boolean hasProfilePicture() {
    return Objects.nonNull(profilePicture);
  }

  private void setProfilePicture(ProfilePicture profilePicture) {
    this.profilePicture = profilePicture;
  }

  /**
   * 입력된 비밀번호가 일치하는지 검증한다.
   *
   * @param password 비교할 비밀번호
   * @return
   */
  public User verifyPassword(String password) {
    if (Objects.equals(getPassword(), password)) {
      return this;
    }
    throw new UserPasswordNotMatchedException();
  }

  /**
   * 사용자 프로필 이미지를 변경한다.
   *
   * @param profilePicture 변경할 프로필 이미지
   * @return
   */
  public User changeProfilePicture(ProfilePicture profilePicture) {
    setProfilePicture(profilePicture);
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    User user = (User) obj;
    return Objects.equals(getUsername(), user.getUsername());
  }

  @Override
  public String toString() {
    return "User [username=%s, profilePicture=%s]".formatted(username, profilePicture);
  }

  // for hibernate
  @SuppressWarnings("unused")
  private User() {}

}
