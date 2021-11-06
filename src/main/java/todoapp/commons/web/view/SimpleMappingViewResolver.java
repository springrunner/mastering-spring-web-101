package todoapp.commons.web.view;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * 뷰 이름(ViewName)이 대한 뷰를 반환하는 단순한 뷰 리졸버 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class SimpleMappingViewResolver implements ViewResolver {
    
    private final Map<String, View> viewMappings;

    public SimpleMappingViewResolver(Map<String, View> viewMappings) {
        this.viewMappings = viewMappings;
    }

    public SimpleMappingViewResolver add(String viewName, View view) {
        viewMappings.remove(viewName);
        viewMappings.put(viewName, view);
        return this;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (viewMappings.containsKey(viewName)) {
            return viewMappings.get(viewName);
        }
        return null;
    }

}
