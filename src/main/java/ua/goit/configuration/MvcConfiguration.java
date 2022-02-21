package ua.goit.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ua.goit.configuration.converters.RoleStringConverter;
import ua.goit.configuration.converters.StringRoleConverter;
import ua.goit.roles.RoleDto;

import java.util.Locale;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    StringRoleConverter stringRoleConverter;
    RoleStringConverter roleStringConverter;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

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