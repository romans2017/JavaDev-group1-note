package ua.goit.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;

@Service
public class UserService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

  @Transactional
  boolean existsById(UUID id) {
    return userRepository.existsById(id);
  }

  public List<User> getAllUsers() {
    LOGGER.info(String.valueOf(userRepository.findAll()));
    return userRepository.findAll();
  }

  @Transactional
  public User getUser(UUID id) {
    System.out.println("User: " + userRepository.getById(id));
    return userRepository.getById(id);
  }

  public List<User> findAll(int pageNumber, int rowPerPage) {
    List<User> users = new ArrayList<>();
    Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
        Sort.by("id").ascending());
    userRepository.findAll(sortedByIdAsc).forEach(users::add);
    return users;
  }

  @Transactional
  public User save(User user) throws BadResourceException, ResourceAlreadyExistsException {
    if (!StringUtils.isEmpty(user.getUserName())) {
      if (user.getId() != null && existsById(user.getId())) {
        throw new ResourceAlreadyExistsException("User with id: " + user.getId() + " already exists");
      }
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      System.out.print("Save user = " + user);
      return userRepository.save(user);
    } else {
      BadResourceException exc = new BadResourceException("Failed to save user");
      exc.addErrorMessage("User is null or empty");
      throw exc;
    }
  }

  @Transactional
  public void update(User user) throws BadResourceException, ResourceNotFoundException {
    User userDb = userRepository.getById(user.getId());
    if (!StringUtils.isEmpty(user.getUserName())) {
      if (!existsById(user.getId())) {
        throw new ResourceNotFoundException("Cannot find User with id: " + user.getId());
      }
      if (StringUtils.hasText(user.getPassword())) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      }
      user.setPassword(userDb.getPassword());
      userRepository.save(user);
    }
    else {
      BadResourceException exc = new BadResourceException("Failed to save user");
      exc.addErrorMessage("User is null or empty");
      throw exc;
    }
  }

  @Transactional
  public void deleteById(UUID id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find user with id: " + id);
    }
    else {
      userRepository.deleteById(id);
    }
  }

  public Long count() {
    return userRepository.count();
  }
}