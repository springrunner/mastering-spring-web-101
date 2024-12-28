package todoapp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSessionHolder;
import todoapp.security.web.servlet.RolesVerifyHandlerInterceptor;
import todoapp.security.web.servlet.UserSessionFilter;
import todoapp.web.support.method.ProfilePictureReturnValueHandler;
import todoapp.web.support.method.UserSessionHandlerMethodArgumentResolver;
import todoapp.web.support.servlet.error.ReadableErrorAttributes;
import todoapp.web.support.servlet.handler.ExecutionTimeHandlerInterceptor;
import todoapp.web.support.servlet.handler.LoggingHandlerInterceptor;
import todoapp.web.support.servlet.view.CommaSeparatedValuesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web MVC 설정 정보이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private ProfilePictureStorage profilePictureStorage;

    @Autowired
    private UserSessionHolder userSessionHolder;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingHandlerInterceptor());
        registry.addInterceptor(new ExecutionTimeHandlerInterceptor());
        registry.addInterceptor(new RolesVerifyHandlerInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserSessionHandlerMethodArgumentResolver(userSessionHolder));
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new ProfilePictureReturnValueHandler(profilePictureStorage));
    }

    @Bean
    ErrorAttributes errorAttributes(MessageSource messageSource) {
        return new ReadableErrorAttributes(messageSource);
    }

    @Bean
    FilterRegistrationBean<UserSessionFilter> userSessionFilterRegistrationBean() {
        var registrationBean = new FilterRegistrationBean<UserSessionFilter>();
        registrationBean.setFilter(new UserSessionFilter(userSessionHolder));
        return registrationBean;
    }

    /**
     * 스프링부트가 생성한 `ContentNegotiatingViewResolver`를 조작할 목적으로 작성된 설정 정보이다.
     */
    @Configuration
    static class ContentNegotiationCustomizer {

        @Autowired
        void configure(ContentNegotiatingViewResolver viewResolver) {
            var defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
            defaultViews.add(new CommaSeparatedValuesView());
            defaultViews.add(new MappingJackson2JsonView());

            viewResolver.setDefaultViews(defaultViews);
        }

    }

}
