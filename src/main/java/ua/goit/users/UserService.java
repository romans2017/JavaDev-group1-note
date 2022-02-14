package ua.goit.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;

import java.util.UUID;

@Service
public class UserService extends BaseService<User, UUID> {
  public UserService(CrudRepository<User, UUID> repository) {
    super(repository);
  }
}