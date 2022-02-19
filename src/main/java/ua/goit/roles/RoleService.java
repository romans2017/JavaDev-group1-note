package ua.goit.roles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.base.BaseService;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService extends BaseService<Role, RoleDto> {

    private final RoleRepository roleRepository;

    boolean existsById(UUID id) {
        return roleRepository.existsById(id);
    }

    boolean existsByName(String name) {
        return roleRepository.existsByNameIgnoreCase(name);
    }

    @Transactional
    public RoleDto save(RoleDto role) throws ResourceAlreadyExistsException, BadResourceException {
        if (!role.getName().isEmpty()) {
            if (role.getId() != null && existsById(role.getId())) {
                throw new ResourceAlreadyExistsException("Role with id: " + role.getId() +
                        " already exists");
            }
            return modelMapper.map(
                    roleRepository.save(
                            modelMapper.map(role, Role.class)), RoleDto.class);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save user");
            exc.addErrorMessage("Role is null or empty");
            throw exc;
        }
    }

    @Transactional
    public RoleDto create(RoleDto role) throws ResourceAlreadyExistsException {
        Role roleDb = roleRepository.findByName(role.getName());
        if (roleDb.getId() != null) {
            throw new ResourceAlreadyExistsException("Role with id: " + role.getId() + " already exists");
        }
        return modelMapper.map(
                roleRepository.save(
                        modelMapper.map(role, Role.class)), RoleDto.class);
    }
}