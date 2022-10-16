package ru.gbteam.lms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.gbteam.lms.converter.RoleDtoByStringConverter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
    private final RoleDtoByStringConverter roleDtoByStringConverter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleDtoByStringConverter);
    }

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodeFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean =
                new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        registrationBean.setUrlPatterns(List.of("/*"));
        return registrationBean;
    }
}
