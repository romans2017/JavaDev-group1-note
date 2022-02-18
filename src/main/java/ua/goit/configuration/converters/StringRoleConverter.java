package ua.goit.configuration.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goit.roles.RoleDto;
import ua.goit.roles.RoleService;

import java.util.UUID;

@Component
public class StringRoleConverter implements Converter<String, RoleDto> {

    @Autowired
    private RoleService roleService;

    @Override
    public RoleDto convert(String source) {
        return roleService.find(UUID.fromString(source));
    }
}