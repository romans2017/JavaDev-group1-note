package ua.goit.roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;

import java.util.UUID;

@Service
public class RoleService extends BaseService<Role, RoleDto> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RoleRepository repository;

    boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    boolean existsByName(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean isExist(BaseDto dto) {
        if (dto.getId() == null) {
            return repository.existsByNameIgnoreCase(((RoleDto) dto).getName());
        } else {
            return repository.existsByNameIgnoreCaseAndIdIsNot(((RoleDto) dto).getName(), dto.getId());
        }
    }
}