package todoapp.core.user.domain.support;

import org.springframework.stereotype.Component;
import todoapp.core.shared.identifier.UserId;
import todoapp.core.user.domain.UserIdGenerator;

import java.util.UUID;

/**
 * UUID 사용자 식별자 생성기
 *
 * @author springrunner.kr@gmail.com
 */
@Component
class UUIDBasedUserIdGenerator implements UserIdGenerator {

    @Override
    public UserId generateId() {
        return UserId.of(UUID.randomUUID().toString());
    }

}
