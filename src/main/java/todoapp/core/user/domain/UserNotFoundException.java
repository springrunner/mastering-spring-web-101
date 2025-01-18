package todoapp.core.user.domain;

/**
 * 사용자 저장소에서 사용자 엔티티를 찾을 수 없을 때 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class UserNotFoundException extends UserException {

    private final String username;

    public UserNotFoundException(String username) {
        super(String.format("user entity not found (username: %s)", username));
        this.username = username;
    }

    @Override
    public Object[] getArguments() {
        return new Object[]{String.valueOf(username)};
    }

}
