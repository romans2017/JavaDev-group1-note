package ua.goit.base;


import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseService <T extends BaseEntity, UUID>{

    private final CrudRepository<T, UUID> repository;

    public T save(T entity) {
        return repository.save (entity);
    }

    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
