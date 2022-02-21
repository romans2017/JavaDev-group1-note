package ua.goit.users;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;
import ua.goit.roles.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class UserService extends BaseService<User, UserDto> {

    UserRepository repository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserDto dto) {
        User model = modelMapper.map(dto, User.class);
        model.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRoles().isEmpty()) {
            model.setRoles(List.of(roleRepository.findByNameIgnoreCase("user")));
        }
        return modelMapper.map(repository.save(model), UserDto.class);
    }

    @Override
    public void update(UUID id, UserDto dto) {
        repository.findById(id)
                .map(user -> {user.setRoles(new ArrayList<>());
                    modelMapper.map(dto, user);
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    return user;
                }).ifPresent(repository::save);
    }

    public UserDto findByName(String name) {
        return modelMapper.map(repository.findUserByName(name), UserDto.class);
    }

    @Override
    public boolean isExist(BaseDto dto) {
        if (dto.getId() == null) {
            return repository.existsByNameIgnoreCase(dto.getName());
        } else {
            return repository.existsByNameIgnoreCaseAndIdIsNot(dto.getName(), dto.getId());
        }
    }

    public boolean isExistByRoleId(UUID role_id) {
        return repository.existsByRoles_Id(role_id);
    }
}