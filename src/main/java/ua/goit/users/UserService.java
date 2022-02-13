package ua.goit.users;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;

@Service
public class UserService extends BaseService<User, UUID> {

  public UserService(
      CrudRepository<User, UUID> repository) {
    super(repository);
  }
}