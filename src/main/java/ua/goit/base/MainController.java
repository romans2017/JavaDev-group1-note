package ua.goit.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/registration")
    public String register() {
        return "/registration";
    }

    /*@GetMapping("/notes")
    public String notes() {
        return "note/notes";
    }

    @GetMapping("/notes/note")
    public String note() {
        return "note/note";
    }

    @GetMapping("/note/noteshare")
    public String noteshare() {
        return "note/notesshare";
    }*/

    @GetMapping("/users")
    public String users() {
        return "user/users";
    }

    @GetMapping("/users/user")
    public String user() {
        return "user/user";
    }
}
