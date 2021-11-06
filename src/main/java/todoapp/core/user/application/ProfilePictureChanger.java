package todoapp.core.user.application;

import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.User;

/**
 * 사용자 프로필 이미지 변경기 인터페이스
 *
 * @author springrunner.kr@gmail.com
 */
public interface ProfilePictureChanger {

    /**
     * 사용자 이름으로 사용자를 찾아 프로필 이미지를 변경합니다.
     *
     * @param username 사용자 이름
     * @param profilePicture 프로필 이미지 개체
     * @return 변경 대상 사용자 개체
     */
    User change(String username, ProfilePicture profilePicture);

}
