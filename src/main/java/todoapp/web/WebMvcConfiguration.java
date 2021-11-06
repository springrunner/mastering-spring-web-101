package todoapp.web;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import todoapp.web.TodoController.TodoCsvView;

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
        
        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }
    
    @Bean(name = "todos")
    public TodoCsvView todos() {
        return new TodoCsvView();
    }

    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 컴포넌트
     */
    public static class ContentNegotiationCustomizer {

        public void configurer(ContentNegotiatingViewResolver viewResolver) {
            // TODO ContentNegotiatingViewResolver 사용자 정의
        }

    }

}
