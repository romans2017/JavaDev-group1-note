package ua.goit.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;

@Service
public class RoleService extends BaseService<Role, RoleDto> {

    @Autowired
    protected RoleRepository repository;

    @Override
    public boolean isExist(BaseDto dto) {
        if (dto.getId() == null) {
            return repository.existsByNameIgnoreCase(((RoleDto) dto).getName());
        } else {
            return repository.existsByNameIgnoreCaseAndIdIsNot(((RoleDto) dto).getName(), dto.getId());
        }
    }
}