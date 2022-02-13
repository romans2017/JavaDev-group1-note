package ua.goit.notes;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("ua.goit")
@DataJpaTest
@ActiveProfiles("dev")
class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @Test
    public void insertNote() {
        NoteDto noteDto = new NoteDto();
        noteDto.setName("test note");
        noteDto.setDescription("bla bla bla");

        NoteDto saved = noteService.create(noteDto);
        NoteDto found = noteService.find(saved.getId());

        assertThat(found).isEqualTo(saved);
    }

    @Test
    public void updateNote() {

        NoteDto noteDto = new NoteDto();
        noteDto.setName("test note");
        noteDto.setDescription("bla bla bla");

        NoteDto created = noteService.create(noteDto);
        noteDto.setDescription("la la la");
        noteService.update(created.getId(), noteDto);
        NoteDto updated = noteService.find(created.getId());

        assertThat(updated).isEqualTo(noteDto);
    }

    @Test
    public void getNote() {

        NoteDto noteDto = new NoteDto();
        noteDto.setName("test note");
        noteDto.setDescription("bla bla bla");

        NoteDto created = noteService.create(noteDto);
        noteDto.setDescription("la la la");
        noteService.update(created.getId(), noteDto);
        NoteDto updated = noteService.find(created.getId());

        assertThat(updated.getId()).isEqualTo(noteDto.getId());
    }

    @Test
    public void deleteNote() {

        NoteDto noteDto = new NoteDto();
        noteDto.setName("test note");
        noteDto.setDescription("bla bla bla");

        NoteDto created = noteService.create(noteDto);
        UUID uuid = created.getId();
        noteService.delete(uuid);
        NoteDto found = noteService.find(uuid);

        assertThat(found).isEqualTo(new NoteDto());
    }

}