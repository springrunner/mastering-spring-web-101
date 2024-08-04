package todoapp.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import todoapp.commons.web.view.CommaSeparatedValuesView;
import todoapp.core.todo.domain.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web MVC 설정 정보이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // registry.addResourceHandler("/assets/**").addResourceLocations("assets/");
        // registry.addResourceHandler("/assets/**").addResourceLocations("file:./files/assets/");
        // registry.addResourceHandler("/assets/**").addResourceLocations("classpath:assets/");
        // registry.addResourceHandler("/assets/**").addResourceLocations("assets/", "file:./files/assets/", "classpath:assets/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.viewResolver(new TodoController.TodoCsvViewResolver());

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

    // BeanNameViewResolver로 뷰 객체 제공하기 
    // @Bean(name = "todos")
    // public CommaSeparatedValuesView commaSeparatedValuesView() {
    //     return new CommaSeparatedValuesView();
    // }

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
