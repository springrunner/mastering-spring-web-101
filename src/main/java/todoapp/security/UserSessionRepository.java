package todoapp.security;

/**
 * 사용자 세션 저장소 인터페이스
 *
 * @author springrunner.kr@gmail.com
 */
public interface UserSessionRepository {

    UserSession get();
    void set(UserSession session);
    void clear();

}