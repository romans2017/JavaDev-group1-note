package ua.goit.roles;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;

import java.util.UUID;

@Service
public class RoleService extends BaseService<Role, UUID> {
  public RoleService(CrudRepository<Role, UUID> repository) {
    super(repository);
  }
}