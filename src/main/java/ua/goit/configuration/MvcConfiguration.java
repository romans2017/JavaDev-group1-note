package ua.goit.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.goit.configuration.converters.RoleStringConverter;
import ua.goit.configuration.converters.StringRoleConverter;
import ua.goit.roles.RoleDto;

@RequiredArgsConstructor
@Configuration
@RequiredArgsConstructor
public class MvcConfiguration implements WebMvcConfigurer {

    private final StringRoleConverter stringRoleConverter;
    private final RoleStringConverter roleStringConverter;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(RoleDto.class, String.class, roleStringConverter);
        registry.addConverter(String.class, RoleDto.class, stringRoleConverter);
    }
}