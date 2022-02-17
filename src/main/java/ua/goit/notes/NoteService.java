package ua.goit.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;

@Service
public class NoteService extends BaseService<Note, NoteDto> {

    @Autowired
    protected NoteRepository repository;
}
