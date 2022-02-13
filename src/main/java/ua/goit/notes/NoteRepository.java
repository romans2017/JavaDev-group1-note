package ua.goit.notes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface NoteRepository extends CrudRepository<Note, UUID> {
}
