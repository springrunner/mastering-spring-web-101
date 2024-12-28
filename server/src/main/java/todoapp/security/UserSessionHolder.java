package todoapp.security;

/**
 * 사용자 세션을 관리하기 위한 인터페이스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface UserSessionHolder {

    /**
     * 저장된 사용자 세션을 불러옵니다.
     * 저장된 사용자 세션이 없으면 {@literal null} 을 반환합니다.
     *
     * @return 사용자 세션
     */
    UserSession get();

    /**
     * 사용자 세션을 저장합니다.
     *
     * @param session 사용자 세션
     */
    void set(UserSession session);

    /**
     * 사용자 세션을 초기화합니다 .
     */
    void reset();

}
