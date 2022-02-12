package ua.goit.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ua.goit.notes.AccssesType.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public String getAllNotes(Model model) {
        List<Note> all = noteService.findAll();
        model.addAttribute("notes", all);
        model.addAttribute("countNotes", all == null ? 0 : all.size());
        return "note/notes";
    }

    @GetMapping("/create")
    public String saveNote(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("create",true);
        return "note/create_note";
    }

    @PostMapping("/save_note")
    public String saveNote(@ModelAttribute("note") @Valid Note note, BindingResult result,Model model) {
        if (result.hasErrors()) {
            model.addAttribute("note", note);
            model.addAttribute("create",true);
            return "note/create_note";
        }
        noteService.save(note);
        return "redirect:/note/list";
    }

    @GetMapping("/edit_note/{id}")
    public String editNote(@PathVariable(value = "id") UUID id, Model model) {
        noteService.findById(id).ifPresent(note -> {
            model.addAttribute("note", note);
        });
        model.addAttribute("create",false);
        return "note/create_note";
    }

    @GetMapping("/remove_note/{id}")
    public String removeNote(@PathVariable(value = "id") UUID id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }
    @GetMapping("/show_note/{id}")
    public String showNote(@PathVariable(value = "id") UUID id, Model model){
        noteService.findById(id).ifPresent(note -> {
            model.addAttribute("note", note);
        });

        return "note/show_note";
    }
}
