package ua.goit.notes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;

import java.util.UUID;

@Service
public class NoteService extends BaseService<Note, UUID> {
    public NoteService(CrudRepository<Note, UUID> repository) {
        super(repository);
    }
}
