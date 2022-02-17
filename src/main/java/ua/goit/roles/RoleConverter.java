package ua.goit.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goit.users.UserService;

import java.util.UUID;

@Component
public class RoleConverter implements Converter<String,RoleDto> {
    @Autowired
    private RoleService roleService;
    @Override
    public RoleDto convert(String id) {
        UUID uuid = UUID.fromString(id);
     return    roleService.find(uuid);
    }
}
