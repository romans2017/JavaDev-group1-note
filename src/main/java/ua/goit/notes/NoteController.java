package ua.goit.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ua.goit.notes.AccessType.PUBLIC;

@RequiredArgsConstructor
@Controller
@RequestMapping("note")
public class NoteController {

    @Autowired
    private final NoteService noteService;
    @Autowired
    private final HtmlService htmlService;

    private List<NoteDto> getNotes() {
        return noteService.findAll();
    }

    private List<NoteDto> getUserNotes(Authentication authentication) {
        return getNotes().stream()
                .filter(userNotes -> authentication.getName()
                        .equals(userNotes.getUser().getUserName()))
                .collect(Collectors.toList());
    }

    @GetMapping("list")
    public String getAllNotes(Model model, Authentication authentication) {
        List<NoteDto> notes = getUserNotes(authentication);
        notes.forEach(note->{
            String text = htmlService.markdownToText(note.getText());
            note.setText(text);
        });
        model.addAttribute("notes", notes);
        model.addAttribute("countNotes", notes.size());
        return "note/notes";
    }

    @GetMapping("create")
    public String saveNote(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("create", true);
        return "note/create_note";
    }

    @PostMapping("save_note")
    public String saveNote(@ModelAttribute("note") @Valid NoteDto note, BindingResult result, Model model) throws BadResourceException, ResourceAlreadyExistsException {
        if (result.hasErrors()) {
            model.addAttribute("note", note);
            model.addAttribute("create", true);
            return "note/create_note";
        }
        noteService.create(note);
        return "redirect:/note/list";
    }

    @GetMapping("edit_note/{id}")
    public String editNote(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("note", noteService.find(id));
        model.addAttribute("create", false);
        return "note/create_note";
    }

    @GetMapping("remove_note/{id}")
    public String removeNote(@PathVariable(value = "id") UUID id) {
        noteService.delete(id);
        return "redirect:/note/list";
    }

    @GetMapping("show_note/{id}")
    public String showNote(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("note", noteService.find(id));
        model.addAttribute("htmlContent", htmlService.markdownToHtml(noteService.find(id).getText()));
        return "note/show_note";
    }

    @GetMapping("share/{id}")
    public String showNoteByLink(@PathVariable(value = "id") UUID id, Model model) {
        NoteDto note = noteService.find(id);
            if (note.getAccessType().equals(PUBLIC)) {
                String userName = SecurityContextHolder.getContext().getAuthentication().getName();
                model.addAttribute("note", note);
                model.addAttribute("userName",userName);
                String htmlContent = htmlService.markdownToHtml(note.getText());
                model.addAttribute("htmlContent",htmlContent);
                return "note/note_share";
            }
        return "redirect:/login";
    }
}