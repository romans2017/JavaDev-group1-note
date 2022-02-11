package ua.goit.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public String getAllNotes(Model model){
        List<Note> all = noteService.findAll();
        model.addAttribute("notes",all);
        return "notes";
    }
}
