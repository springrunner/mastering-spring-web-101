package todoapp.core.user.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

/**
 * 사용자 프로필 이미지 값 객체(Value Object)이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Embeddable
public class ProfilePicture implements Serializable {

    private URI uri;

    public ProfilePicture(URI uri) {
        setUri(Objects.requireNonNull(uri));
    }

    // for hibernate
    @SuppressWarnings("unused")
    private ProfilePicture() {
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
        return Objects.equals(uri, ((ProfilePicture) obj).uri);
    }

    @Override
    public String toString() {
        return uri.toString();
    }

}
