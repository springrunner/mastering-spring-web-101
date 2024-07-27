package todoapp.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import todoapp.web.support.ConnectedClientCountBroadcaster;

/**
 * 실시간 사이트 접속 사용자 수 카운터 컨트롤러이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Controller
public class OnlineUsersCounterController {

    private final ConnectedClientCountBroadcaster broadcaster = new ConnectedClientCountBroadcaster();

    /*
     * HTML5 Server-sent events(https://en.wikipedia.org/wiki/Server-sent_events) 명세를 구현했다.
     */
    @RequestMapping(path = "/stream/online-users-counter", produces = "text/event-stream")
    public SseEmitter counter(HttpServletResponse response) throws Exception {
        return broadcaster.subscribe();
    }

}
