package todoapp.core.user.domain;

/**
 * 사용자 프로필 이미지 저장 실패시 발생 가능한 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public class FailedSavingProfilePictureException extends UserEntityException {
    
    private static final long serialVersionUID = 1L;

    public FailedSavingProfilePictureException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
