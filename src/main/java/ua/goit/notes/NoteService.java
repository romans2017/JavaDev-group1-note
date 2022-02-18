package ua.goit.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.goit.base.BaseService;
import ua.goit.users.User;
import ua.goit.users.UserRepository;

@RequiredArgsConstructor
@Service
public class NoteService extends BaseService<Note, NoteDto> {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;


    @Override
    public NoteDto create(NoteDto dto){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(userName);
        Note note = super.modelMapper.map(dto, Note.class);
        note.setUser(user);
        noteRepository.save(note);
        return super.modelMapper.map(note,NoteDto.class);
    }
}