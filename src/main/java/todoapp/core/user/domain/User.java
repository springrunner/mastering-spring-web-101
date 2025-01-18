package todoapp.core.user.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import todoapp.core.shared.identifier.UserId;

import java.io.Serializable;
import java.util.Objects;

/**
 * 사용자 엔티티(Entity)
 *
 * @author springrunner.kr@gmail.com
 */
@Entity(name = "users")
public class User implements Serializable {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private UserId id;
    private String username;
    private String password;
    private ProfilePicture profilePicture;

    public User(UserId id, String username, String password) {
        this.id = Objects.requireNonNull(id, "id must be not null");
        this.username = Objects.requireNonNull(username, "username must be not null");
        this.password = Objects.requireNonNull(password, "password must be not null");
    }

    // for hibernate
    @SuppressWarnings("unused")
    private User() {
    }

    public UserId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public boolean hasProfilePicture() {
        return Objects.nonNull(profilePicture);
    }

    /**
     * 입력된 비밀번호가 일치하는지 검증한다.
     *
     * @param password 비교할 비밀번호
     * @return 비밀번호가 일치하면 현재 사용자 객체, 그렇지 않으면 예외 발생
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
     * @return 프로필 이미지가 변경된 현재 사용자 객체
     */
    public User changeProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object user) {
        if (this == user)
            return true;
        if (user == null || getClass() != user.getClass())
            return false;
        return Objects.equals(getId(), ((User) user).getId());
    }

    @Override
    public String toString() {
        return "User [username=%s, profile-picture=%s]".formatted(username, profilePicture);
    }

}
