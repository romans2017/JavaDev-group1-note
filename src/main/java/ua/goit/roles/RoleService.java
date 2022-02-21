package ua.goit.roles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;

@RequiredArgsConstructor
@Service
public class RoleService extends BaseService<Role, RoleDto> {

    private final RoleRepository repository;

    @Override
    public boolean isExist(BaseDto dto) {
        if (dto.getId() == null) {
            return repository.existsByNameIgnoreCase(dto.getName());
        } else {
            return repository.existsByNameIgnoreCaseAndIdIsNot(dto.getName(), dto.getId());
        }
    }
}