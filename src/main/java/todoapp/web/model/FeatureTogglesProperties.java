package todoapp.web.model;

/**
 * 기능 토글 모델
 *
 * @author springrunner.kr@gmail.com
 */
public class FeatureTogglesProperties {

    private boolean auth = false;
    private boolean onlineUsersCounter = false;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isOnlineUsersCounter() {
        return onlineUsersCounter;
    }

    public void setOnlineUsersCounter(boolean onlineUsersCounter) {
        this.onlineUsersCounter = onlineUsersCounter;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeatureTogglesProperties{");
        sb.append("auth=").append(auth);
        sb.append(", onlineUsersCounter=").append(onlineUsersCounter);
        sb.append('}');
        return sb.toString();
    }

}
