package ua.goit.configuration.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goit.roles.RoleDto;
import ua.goit.roles.RoleService;

@Component
public class RoleStringConverter implements Converter<RoleDto, String> {

    @Autowired
    private RoleService roleService;

    @Override
    public String convert(RoleDto role) {
        return role.getId().toString();
    }
}
