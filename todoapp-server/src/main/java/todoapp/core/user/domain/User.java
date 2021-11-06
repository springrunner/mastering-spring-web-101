package todoapp.core.user.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * 사용자 엔티티(Entity)
 *
 * @author springrunner.kr@gmail.com
 */
@Entity
public class User {

    @Id
    private String username;
    private String password;
    private ProfilePicture profilePicture;

    // for hibernate
    @SuppressWarnings("unused")
    private User() { }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
