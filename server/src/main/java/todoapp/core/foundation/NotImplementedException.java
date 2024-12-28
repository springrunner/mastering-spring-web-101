package todoapp.core.foundation;

/**
 * 메서드 내부가 구현되지 않은 경우 발생할 수 있는 예외 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class NotImplementedException extends SystemException {

    public NotImplementedException() {
        super("method is not yet implemented");
    }

    public NotImplementedException(String format, Object[] args) {
        super(format, args);
    }

}
