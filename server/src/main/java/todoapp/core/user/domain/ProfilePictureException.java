package todoapp.core.user.domain;

/**
 * 사용자 프로필 이미지를 처리하는 과정에서 발생 가능한 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class ProfilePictureException extends UserException {

    public ProfilePictureException(String format, Object... args) {
        super(format, args);
    }

    public ProfilePictureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 사용자 프로필 이미지 저장 실패시 발생 가능한 예외 클래스이다.
     *
     * @author springrunner.kr@gmail.com
     */
    public static class ProfilePictureSaveException extends ProfilePictureException {

        public ProfilePictureSaveException(String message) {
            super(message);
        }

        public ProfilePictureSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 사용자 프로필 이미지 불러오기 실패시 발생 가능한 예외 클래스이다.
     *
     * @author springrunner.kr@gmail.com
     */
    public static class ProfilePictureLoadFailedException extends ProfilePictureException {

        public ProfilePictureLoadFailedException(String message) {
            super(message);
        }

        public ProfilePictureLoadFailedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
