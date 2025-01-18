package todoapp.core.user.application;

import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.User;

/**
 * 사용자 프로필 이미지를 변경하기 위한 유스케이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface ChangeUserProfilePicture {

    /**
     * 지정된 사용자 이름에 해당하는 사용자의 프로필 이미지를 변경한다.
     *
     * @param username       사용자 이름
     * @param profilePicture 프로필 이미지 객체
     * @return 프로필 이미지가 변경된 사용자 객체
     */
    User change(String username, ProfilePicture profilePicture);

}
