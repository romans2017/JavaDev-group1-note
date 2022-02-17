package ua.goit.users;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.roles.Role;
import ua.goit.roles.RoleRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("registration")
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private List<Role> initRoles() {
        return roleRepository.findAll();
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("getRoles", initRoles());
        return "registration";
    }

    @PostMapping
    public String registrationUser(@Valid @ModelAttribute User user,
                                   BindingResult result,
                                   Model model) {

        User userFromDb = userRepository.findByName(user.getName());
        model.addAttribute("getRoles", initRoles());
        if (result.hasErrors() || user.getRoles() == null || userFromDb != null) {
            model.addAttribute("message", "Something wrong! Errors: " + result.getFieldErrors().size());
            result
                    .getFieldErrors()
                    .forEach(f -> model.addAttribute(f.getField(), f.getDefaultMessage()));

            result
                    .getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
            if (user.getRoles() == null) {
                model.addAttribute("errorRoles", "User has minimum one role!");
                return "registration";
            } else if (userFromDb != null) {
                model.addAttribute("errorUniqueUserName", "This user name is exists! User name must be unique!");
                return "registration";
            }
            return "registration";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}