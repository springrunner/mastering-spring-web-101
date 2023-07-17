package todoapp.web.support.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import todoapp.core.foundation.NotImplementedException;
import todoapp.core.user.domain.ProfilePictureStorage;

import java.util.Objects;

/**
 * 스프링 MVC 핸들러 인수로 사용자 세션 객체를 제공하기 위해 작성된 컴포넌트입니다.
 *
 * @author springrunner.kr@gmail.com
 */
public class ProfilePictureReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final ProfilePictureStorage profilePictureStorage;

    public ProfilePictureReturnValueHandler(ProfilePictureStorage profilePictureStorage) {
        this.profilePictureStorage = Objects.requireNonNull(profilePictureStorage);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        throw new NotImplementedException();
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        throw new NotImplementedException();
    }

}
