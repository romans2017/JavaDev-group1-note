package ua.goit.notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
}
