package ua.goit.base;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;
import ua.goit.users.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseService <T extends BaseEntity, UUID>{

    private final JpaRepository<T, UUID> repository;

    public T save(T entity) throws BadResourceException, ResourceAlreadyExistsException {
        return repository.save (entity);
    }

    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id)  throws ResourceNotFoundException {
        repository.deleteById(id);
    }

}
