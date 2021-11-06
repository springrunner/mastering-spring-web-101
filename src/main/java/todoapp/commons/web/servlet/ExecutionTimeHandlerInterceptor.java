package todoapp.commons.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 핸들러 실행 시간을 측정하는 인터셉터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class ExecutionTimeHandlerInterceptor implements HandlerInterceptor, Ordered {

    private static final String STOP_WATCH_ATTR_NAME = "ExecutionTimeHandlerInterceptor.StopWatch";
    
    private final Logger log = LoggerFactory.getLogger(ExecutionTimeHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch(getHandlerName(handler));
        stopWatch.start();
        request.setAttribute(STOP_WATCH_ATTR_NAME, stopWatch);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        StopWatch stopWatch = (StopWatch) request.getAttribute(STOP_WATCH_ATTR_NAME);
        stopWatch.stop();

        log.debug("[" + getHandlerName(handler) + "] executeTime : " + stopWatch.getTotalTimeMillis() + "ms");
    }

    private String getHandlerName(Object handler) {
        if (handler instanceof HandlerMethod) {
            return ((HandlerMethod) handler).getShortLogMessage();
        }
        return handler.toString();
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
