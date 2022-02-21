package ua.goit.roles;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseService<Role, RoleDto> {

    protected RoleRepository repository;

    @Autowired
    public void setRepository(RoleRepository repository) {
        this.repository = repository;
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