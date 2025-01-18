package todoapp.core.user.domain;

/**
 * 사용자 비밀번호 검증시 일치하지 않으면 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class UserPasswordNotMatchedException extends UserException {

    public UserPasswordNotMatchedException() {
        super("entered password does not match");
    }

}
