package todoapp.web.support;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Server-Sent Events 방식으로 연결된 클라이언트 수를 전파하는 컴포넌트
 *
 * @author springrunner.kr@gmail.com
 */
public class ConnectedClientCountBroadcaster {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            broadcast();
        });
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            broadcast();
        });

        emitters.add(emitter);
        broadcast();

        return emitter;
    }

    private void broadcast() {
        for(SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(emitters.size()));
            } catch (IllegalStateException| ClientAbortException ignore) {
                // timeout or completion state
                log.warn("unstable event stream connection (reason: {})", ignore.getMessage());
                emitters.remove(emitter);
            } catch (Exception ignore) {
                log.error("failed to broadcast event to emitter (reason: " + ignore.getMessage() + ")");
                emitters.remove(emitter);
            }
        }
    }

}
