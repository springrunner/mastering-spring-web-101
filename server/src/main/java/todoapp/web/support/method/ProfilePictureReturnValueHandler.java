package todoapp.web.support.method;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;

import java.util.Objects;

/**
 * 스프링 MVC 핸들러 반환값으로 프로필 사진 객체를 처리하기 위해 작성된 컴포넌트입니다.
 *
 * @author springrunner.kr@gmail.com
 */
public class ProfilePictureReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProfilePictureStorage profilePictureStorage;

    public ProfilePictureReturnValueHandler(ProfilePictureStorage profilePictureStorage) {
        this.profilePictureStorage = Objects.requireNonNull(profilePictureStorage);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return ProfilePicture.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        var response = webRequest.getNativeResponse(HttpServletResponse.class);
        var profilePicture = profilePictureStorage.load(((ProfilePicture) returnValue).getUri());
        profilePicture.getInputStream().transferTo(response.getOutputStream());

        mavContainer.setRequestHandled(true);

        log.debug("Response written for profile picture with URI {}", profilePicture.getURI());
    }

}
