package ua.goit.notes;

import com.sun.security.auth.UserPrincipal;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goit.base.CustomUserDetailsService;
import ua.goit.base.MyUserDetails;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;
import ua.goit.users.User;
import ua.goit.users.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static ua.goit.notes.AccessType.PUBLIC;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public String getAllNotes(Model model) {
        List<NoteDto> notes = noteService.findAll();
        model.addAttribute("notes", notes);
        model.addAttribute("countNotes", notes == null ? 0 : notes.size());
        return "note/notes";
    }

    @GetMapping("/create")
    public String saveNote(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("create", true);
        return "note/create_note";
    }

    @PostMapping("/save_note")
    public String saveNote(@ModelAttribute("note") @Valid NoteDto note, BindingResult result, Model model) throws BadResourceException, ResourceAlreadyExistsException {
        if (result.hasErrors()) {
            model.addAttribute("note", note);
            model.addAttribute("create", true);
            return "note/create_note";
        }
        noteService.create(note);
        return "redirect:/note/list";
    }

    @GetMapping("/edit_note/{id}")
    public String editNote(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("note", noteService.find(id));
        model.addAttribute("create", false);
        return "note/create_note";
    }

    @GetMapping("/remove_note/{id}")
    public String removeNote(@PathVariable(value = "id") UUID id) {
        noteService.delete(id);
        return "redirect:/note/list";
    }

    @GetMapping("/show_note/{id}")
    public String showNote(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("note", noteService.find(id));
        return "note/show_note";
    }

    @GetMapping("/share/{id}")
    public String showNoteByLink(@PathVariable(value = "id") UUID id, Model model) {
        NoteDto note = noteService.find(id);
            if (note.getAccessType().equals(PUBLIC)) {
                model.addAttribute("note", note);
                return "note/note_share";
            }
        return "redirect:/login";
    }
}