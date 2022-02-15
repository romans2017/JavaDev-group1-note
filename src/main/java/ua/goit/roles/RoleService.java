package ua.goit.roles;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua.goit.base.BaseService;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;

@Service
public class RoleService extends BaseService<Role,UUID> {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  RoleRepository roleRepository;

  public RoleService(JpaRepository<Role, UUID> repository) {
    super(repository);
  }

  @Transactional
  boolean existsById(UUID id) {
    return roleRepository.existsById(id);
  }

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  @Transactional
  public Role getRole(UUID id) {
    LOGGER.info(String.valueOf(roleRepository.getById(id)));
    return roleRepository.getById(id);
  }

  @Transactional
  @Override
  public Role save(Role role) throws ResourceAlreadyExistsException, BadResourceException {
    if (!StringUtils.isEmpty(role.getName())) {
      if (role.getId() != null && existsById(role.getId())) {
        throw new ResourceAlreadyExistsException("Role with id: " + role.getId() +
            " already exists");
      }
      return roleRepository.save(role);
    }
    else {
      BadResourceException exc = new BadResourceException("Failed to save user");
      exc.addErrorMessage("Role is null or empty");
      throw exc;
    }
  }

  @Transactional
  public Role update(Role role) {
    return roleRepository.save(role);
  }

  public Role create(Role role) throws ResourceAlreadyExistsException {
    Role roleDb = roleRepository.findRoleByName(role.getName());
    if (roleDb.getId() != null) {
      throw new ResourceAlreadyExistsException("Role with id: " + role.getId() + " already exists");
    }
    return roleRepository.save(role);
  }

  @Transactional
  @Override
  public void deleteById(UUID id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find role with id: " + id);
    }
    else {
      roleRepository.deleteById(id);
    }
  }
}