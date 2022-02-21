package ua.goit.configuration.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goit.roles.RoleDto;
import ua.goit.roles.RoleService;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class StringRoleConverter implements Converter<String, RoleDto> {

    private final RoleService roleService;

    @Override
    public RoleDto convert(String source) {
        return roleService.find(UUID.fromString(source));
    }
}