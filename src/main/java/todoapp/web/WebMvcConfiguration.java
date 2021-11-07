package todoapp.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import todoapp.commons.web.error.ReadableErrorAttributes;
import todoapp.commons.web.view.CommaSeparatedValuesView;

/**
 * Spring Web MVC 설정
 *
 * @author springrunner.kr@gmail.com
 */
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {
    
    // 1. 브라우저에서 http://localhost:8080/assets/css/todos.css 로 접근하면
    // 2. todos.css 파일을 볼 수 있어야 함 
    
    /*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations(
            "assets/",
            "file:./files/assets/",
            "classpath:assets/"
        );
    }
    */
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.viewResolver(new TodoController.TodoCsvViewResolver());
        
        // SimpleMappingViewResolver simpleMappingViewResolver = new SimpleMappingViewResolver(new HashMap<>());
        // simpleMappingViewResolver.add("todos", new TodoController.TodoCsvView());
        // registry.viewResolver(simpleMappingViewResolver);
        
        // registry.enableContentNegotiation(new CommaSeparatedValuesView());
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }
    
    /*
    @Bean(name = "todos")
    public CommaSeparatedValuesView todos() {
        // return new TodoCsvView();
        return new CommaSeparatedValuesView();
    }
    */
    
    @Bean
    public ErrorAttributes errorAttributes(MessageSource messageSource) {
        return new ReadableErrorAttributes(messageSource);
    }
    
    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 컴포넌트
     */
    @Component
    public static class ContentNegotiationCustomizer {

        @Autowired
        public void configurer(ContentNegotiatingViewResolver viewResolver) {
            List<View> defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
            defaultViews.add(new CommaSeparatedValuesView());
            defaultViews.add(new MappingJackson2JsonView());
            
            viewResolver.setDefaultViews(defaultViews);
        }

    }

}
