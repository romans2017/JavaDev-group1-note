package ua.goit.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;
import ua.goit.users.User;
import ua.goit.users.UserRepository;

@Service
public class NoteService extends BaseService<Note, NoteDto> {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public NoteDto create(NoteDto dto) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByName(userName);
        Note note = modelMapper.map(dto, Note.class);
        note.setUser(user);
        noteRepository.save(note);
        return modelMapper.map(note, NoteDto.class);
    }
}