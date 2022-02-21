package ua.goit.configuration.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goit.roles.RoleDto;

@Component
public class RoleStringConverter implements Converter<RoleDto, String> {

    @Override
    public String convert(RoleDto role) {
        return role.getId().toString();
    }
}