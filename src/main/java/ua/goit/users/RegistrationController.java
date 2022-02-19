package ua.goit.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.roles.RoleDto;
import ua.goit.roles.RoleService;
import ua.goit.validation.OnCreate;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<RoleDto> getRoleByDefault() {
        return roleService.findAll().stream()
                .filter(roleDefault -> roleDefault.getName().equals("user"))
                .collect(Collectors.toList());
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("user") @Validated({OnCreate.class}) UserDto user,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        user.setRoles(getRoleByDefault());
        if (userService.create(user) == null) {
            return "registration";
        }
        return "redirect:/login";
    }
}