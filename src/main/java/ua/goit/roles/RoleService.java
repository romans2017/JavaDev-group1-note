package ua.goit.roles;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class RoleService extends BaseService<Role, RoleDto> {

    RoleRepository repository;

    @Override
    public boolean isExist(BaseDto dto) {
        if (dto.getId() == null) {
            return repository.existsByNameIgnoreCase(dto.getName());
        } else {
            return repository.existsByNameIgnoreCaseAndIdIsNot(dto.getName(), dto.getId());
        }
    }
}