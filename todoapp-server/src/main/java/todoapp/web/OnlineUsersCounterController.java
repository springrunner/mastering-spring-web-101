package todoapp.web;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import todoapp.web.support.ConnectedClientCountBroadcaster;

/**
 * 실시간 사이트 접속 사용자 수 카운터 컨트롤러
 *
 * @author springrunner.kr@gmail.com
 */
@Controller
public class OnlineUsersCounterController {

    private final ConnectedClientCountBroadcaster broadcaster = new ConnectedClientCountBroadcaster();

    /*
     * HTML5 Server-sent events(https://en.wikipedia.org/wiki/Server-sent_events) 스펙을 구현
     */
    @RequestMapping(path = "/stream/online-users-counter", produces = "text/event-stream")
    public void counter(HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/event-stream");

        for (int number=1; number<11; number++) {
            TimeUnit.SECONDS.sleep(1);

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(("data: " + number + "\n\n").getBytes());
            outputStream.flush();
        }
    }

}
