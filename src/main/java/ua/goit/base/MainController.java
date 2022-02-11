package ua.goit.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.goit.roles.*;
import ua.goit.users.*;

@Controller
public class MainController {

  @Autowired
  UserService userService;

  @Autowired
  RoleService roleService;

  private List<Role> init() {
    return roleService.findAll();
  }

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

  @GetMapping("/notes")
  public String notes() {
    return "note/notes";
  }

  @GetMapping("/notes/note")
  public String note() {
    return "note/note";
  }

  @GetMapping("/users")
  public String users(Model model) {
    List<User> users = userService.findAll();
    model.addAttribute("users", users);
    model.addAttribute("roles", init());
    return "user/users";
  }

  @GetMapping("/users/user")
  public String user() {
    return "user/user";
  }
}