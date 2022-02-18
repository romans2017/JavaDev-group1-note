package ua.goit.roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.base.BaseService;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;

import java.util.UUID;

@Service
public class RoleService extends BaseService<Role, RoleDto> {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  RoleRepository roleRepository;

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
    Role roleDb = roleRepository.findRoleByName(role.getName());
    if (roleDb.getId() != null) {
      throw new ResourceAlreadyExistsException("Role with id: " + role.getId() + " already exists");
    }
    return modelMapper.map(
            roleRepository.save(
                    modelMapper.map(role, Role.class)), RoleDto.class);
  }

  @Transactional
  public void deleteById(UUID id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find role with id: " + id);
    } else {
      roleRepository.deleteById(id);
    }
  }
}