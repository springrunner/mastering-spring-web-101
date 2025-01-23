package todoapp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import todoapp.core.todo.domain.Todo;
import todoapp.web.support.servlet.error.ReadableErrorAttributes;
import todoapp.web.support.servlet.view.CommaSeparatedValuesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web MVC 설정 정보이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 여기서 ResourceHandler를 등록, 핸들러는 스프링 MVC에서 요청을 처리하기 위해 작성된 객체이다.

        // 우리가 원하는 건, 다음 URL로 요청이 들어오면, 파일을 제공하는 것이다.
        // http://localhost:8080/assets/base-CRAHsrqE.css

        // 서블릿 루트 경로에서 제공하기
        // registry.addResourceHandler("/assets/**").addResourceLocations("assets/");

        // 파일 경로에서 제공하기
        // registry.addResourceHandler("/assets/**").addResourceLocations("file:./files/assets/");

        // 클래스패스 경로에서 제공하기
        // registry.addResourceHandler("/assets/**").addResourceLocations("classpath:assets/");

        // 여러(서블릿 루트, 파일, 클래스패스) 경로에서 제공하기
        // registry.addResourceHandler("/assets/**").addResourceLocations("assets/", "file:./files/assets/", "classpath:assets/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var conversionService = new DefaultConversionService();
        conversionService.addConverter(new Converter<Todo, String>() {
            @Override
            public String convert(Todo source) {
                return source.toString();
            }
        });

        converters.add(new ObjectToStringHttpMessageConverter(conversionService));
    }

    @Bean
    ErrorAttributes errorAttributes(MessageSource messageSource) {
        return new ReadableErrorAttributes(messageSource);
    }

    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 설정 정보이다.
     */
    @Configuration
    public static class ContentNegotiationCustomizer {

        @Autowired
        public void configurer(ContentNegotiatingViewResolver viewResolver) {
            var defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
            defaultViews.add(new CommaSeparatedValuesView());
            defaultViews.add(new MappingJackson2JsonView());

            viewResolver.setDefaultViews(defaultViews);
        }

    }

}
