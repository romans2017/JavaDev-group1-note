package ua.goit.notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.goit.configuration.MappingConfiguration;
import ua.goit.configuration.SpringSecurityConfiguration;
import ua.goit.roles.Role;
import ua.goit.roles.RoleRepository;
import ua.goit.roles.RoleService;
import ua.goit.users.UserDto;
import ua.goit.users.UserRepository;
import ua.goit.users.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private RoleRepository roleRepository;
//
//    private final PasswordEncoder passwordEncoder = new SpringSecurityConfiguration().passwordEncoder();
//    private final ModelMapper modelMapper = new MappingConfiguration().modelMapper();
//
//    private UserService userService;
//    private RoleService roleService;
//
//    @BeforeEach
//    void initUseCase() {
//        userService = new UserService(roleRepository, passwordEncoder);
//        userService.setRepository(userRepository);
//        userService.setModelMapper(modelMapper);
//    }
//
//    @Test
//    public void createUser() {
//
//        Role role = new Role();
//        role.setName("user");
//        roleRepository.save(role);
//
//
//        UserDto user = new UserDto();
//        user.setPassword("password");
//        user.setName("test user");
////        RoleDto roleDto = new RoleDto();
////        roleDto.setName();
//        // user.setRoles(List.of(new RoleDto()));
//
//        UserDto saved = userService.create(user);
//        UserDto found = userService.find(saved.getId());
//        assertThat(found).isEqualTo(saved);
//    }
}
