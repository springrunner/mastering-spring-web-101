package todoapp.core.user.domain;

/**
 * 사용자 프로필 이미지 저장 실패시 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class FailedSavingProfilePictureException extends UserEntityException {

    public FailedSavingProfilePictureException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
