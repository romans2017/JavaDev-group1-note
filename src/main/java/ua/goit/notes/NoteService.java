package ua.goit.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;
import ua.goit.exception.ResourceAlreadyExistsException;
import ua.goit.users.User;
import ua.goit.users.UserRepository;

@Service
public class NoteService extends BaseService<Note, NoteDto> {

    @Autowired
    protected NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public NoteDto create(NoteDto dto) throws ResourceAlreadyExistsException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName);
        Note note = super.modelMapper.map(dto, Note.class);
        note.setUser(user);
        noteRepository.save(note);
        return super.modelMapper.map(note,NoteDto.class);
    }
}
