package ua.goit.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;
import ua.goit.roles.Role;
import ua.goit.roles.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, UserDto> {

    protected UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto create(UserDto dto) {
        User model = modelMapper.map(dto, User.class);
        model.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRoles().isEmpty()) {
            model.setRoles(List.of(Optional.ofNullable(roleRepository.findByNameIgnoreCase("user")).orElse(new Role())));
        }
        return modelMapper.map(repository.save(model), UserDto.class);
    }

    public List<UserDto> findAll(int pageNumber, int rowPerPage) {
        List<UserDto> users = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        repository.findAll(sortedByIdAsc).forEach(user -> users.add(
                modelMapper.map(user, UserDto.class)));
        return users;
    }

    @Override
    public void update(UUID id, UserDto dto) {
        repository.findById(id)
                .map(user -> {
                    user.setRoles(new ArrayList<>());
                    modelMapper.map(dto, user);
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    return user;
                }).ifPresent(user -> repository.save(user));
    }

    @Override
    public boolean isExist(BaseDto dto) {
        if (dto.getId() == null) {
            return repository.existsByNameIgnoreCase(((UserDto) dto).getName());
        } else {
            return repository.existsByNameIgnoreCaseAndIdIsNot(((UserDto) dto).getName(), dto.getId());
        }
    }

    public boolean isExistByRoleId(UUID role_id) {
        return repository.existsByRoles_Id(role_id);
    }

    public Long count() {
        return repository.count();
    }
}