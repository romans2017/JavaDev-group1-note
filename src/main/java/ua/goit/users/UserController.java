package ua.goit.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;
import ua.goit.roles.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
//@PreAuthorize("hasAuthority('admin')")
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getUsers(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("countNotes", users == null ? 0 : users.size());
        model.addAttribute("allRoles", roleService.findAll());
        return "user/users";
    }

    @GetMapping("add")
    public String showAddUser(@Valid Model model) {
        UserDto user = new UserDto();
        model.addAttribute("add", true);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "user/user";
    }

    @PostMapping(value = "add")
    public String addUser(Model model, @ModelAttribute("user") @Valid UserDto user,
                          BindingResult result) throws BadResourceException, ResourceAlreadyExistsException {
        boolean isExistByName = userService.existsByName(user.getUserName());
        model.addAttribute("add", true);
        model.addAttribute("allRoles", roleService.findAll());
        if (result.hasErrors() || user.getRoles().size() == 0 || isExistByName) {
            if (user.getRoles().size() == 0) {
                model.addAttribute("errorRoles", "User has minimum one role!");
                return "user/user";
            } else if (isExistByName) {
                model.addAttribute("errorUniqueUserName",
                        "This user name is exists! User name must be unique!");
                return "user/user";
            }
            return "user/user";
        } else {
            userService.save(user);
            return "redirect:/users";
        }
    }

    @GetMapping("{id}")
    public String showEditUser(Model model, @PathVariable UUID id) {
        UserDto user = null;
        model.addAttribute("allRoles", roleService.findAll());
        try {
            user = userService.find(id);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "User not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        return "user/user";
    }

    @PostMapping(value = {"/{userId}"})
    public String updateUser(Model model, @PathVariable UUID userId,
                             @ModelAttribute("user") UserDto user, BindingResult result) {
        model.addAttribute("allRoles", roleService.findAll());
        try {
            if (result.hasErrors()) {
                model.addAttribute("add", false);
                user.setId(userId);
                model.addAttribute("user", user);
                return "user/user";
            } else {
                user.setId(userId);
                userService.update(userId, user);
                return "redirect:/users";
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "user/user";
        }
    }

    @GetMapping("remove_user/{id}")
    public String removeUser(@PathVariable(value = "id") UUID id) {
        userService.delete(id);
        return "redirect:/users";
    }
}