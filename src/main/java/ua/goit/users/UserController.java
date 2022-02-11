package ua.goit.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
////@PreAuthorize("hasAuthority('admin')")
//@RequestMapping("/users")
//public class UserController {
//
//  @Autowired
//  private UserService userService;
//
//  @GetMapping
//  public String getUsers(Model model) {
//    List<User> users = userService.findAll();
//    model.addAttribute("users", users);
//    return "user/users";
//  }
//}