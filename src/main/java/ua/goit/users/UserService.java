package ua.goit.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua.goit.base.BaseService;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;
import ua.goit.roles.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService<User, UserDto> {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  boolean existsById(UUID id) {
    return userRepository.existsById(id);
  }

  boolean existsByName(String name) {
    return userRepository.existsByUserNameIgnoreCase(name);
  }

  public List<UserDto> findAll() {
    List<UserDto> users = new ArrayList<>();
    userRepository.findAll().forEach(user -> users.add(modelMapper.map(user, UserDto.class)));
    return users;
  }

  @Transactional
  public UserDto save(UserDto user) throws BadResourceException, ResourceAlreadyExistsException {
    if (!user.getUserName().isEmpty()) {
      if (user.getId() != null && existsById(user.getId())) {
        throw new ResourceAlreadyExistsException("User with id: " + user.getId() + " already exists");
      }
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      System.out.print("Save user = " + user);
      return modelMapper.map(
              userRepository.save(
                      modelMapper.map(user, User.class)), UserDto.class);
    } else {
      BadResourceException exc = new BadResourceException("Failed to save user");
      exc.addErrorMessage("User is null or empty");
      throw exc;
    }
  }

  @Transactional
  public void update(User user) throws BadResourceException, ResourceNotFoundException {
    User userDb = userRepository.getById(user.getId());
    if (!user.getUserName().isEmpty()) {
      if (!existsById(user.getId())) {
        throw new ResourceNotFoundException("Cannot find User with id: " + user.getId());
      }
      if (StringUtils.hasText(user.getPassword())) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      }
      user.setPassword(userDb.getPassword());
      modelMapper.map(
              userRepository.save(
                      modelMapper.map(user, User.class)), UserDto.class);
    } else {
      BadResourceException exc = new BadResourceException("Failed to save user");
      exc.addErrorMessage("User is null or empty");
      throw exc;
    }
  }
}