package ua.goit.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;

import java.util.UUID;

@Service
public class NoteService extends BaseService<Note, UUID> {

    public NoteService(JpaRepository<Note, UUID> repository) {
        super(repository);
    }

}
