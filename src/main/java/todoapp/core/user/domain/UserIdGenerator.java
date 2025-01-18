package todoapp.core.user.domain;

import todoapp.core.shared.identifier.UserId;

/**
 * 사용자 식별자 생성기
 *
 * @author springrunner.kr@gmail.com
 */
public interface UserIdGenerator {

    UserId generateId();

}
