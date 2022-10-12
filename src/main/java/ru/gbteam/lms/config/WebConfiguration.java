package ru.gbteam.lms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ru.gbteam.lms.converter.RoleDtoByStringConverter;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration extends WebMvcConfigurationSupport {
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
}
