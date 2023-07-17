package todoapp.core.user.domain;

/**
 * 사용자 프로필 이미지 불러오기 실패시 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class FailedLoadingProfilePictureException extends UserEntityException {

    public FailedLoadingProfilePictureException(String format, Object... args) {
        super(format, args);
    }

    public FailedLoadingProfilePictureException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
