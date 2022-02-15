package ua.goit.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;
import ua.goit.roles.RoleDto;
import ua.goit.roles.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
//@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/users")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final int ROW_PER_PAGE = 5;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private List<RoleDto> init() {
        return roleService.findAll();
    }

    @GetMapping
    public String getUsers(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<UserDto> users = userService.findAll(pageNumber, ROW_PER_PAGE);
        Long count = userService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = ((long) pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("users", users);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        model.addAttribute("roles", roleService.findAll());
        return "user/users";
    }

    @GetMapping(value = {"/add"})
    public String showAddUser(@Valid Model model) {
        UserDto user = new UserDto();
        model.addAttribute("add", true);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return "user/user";
    }

    @PostMapping(value = "/add")
    public String addUser(Model model, @ModelAttribute("user") @Valid UserDto user,
                          BindingResult result) throws BadResourceException, ResourceAlreadyExistsException {
        boolean isExistByName = userService.existsByName(user.getUserName());
        model.addAttribute("add", true);
        model.addAttribute("roles", init());
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

    @GetMapping(value = {"/{id}"})
    public String showEditUser(Model model, @PathVariable UUID id) {
        UserDto user = null;
        model.addAttribute("roles", init());
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
        model.addAttribute("roles", init());
        try {
            if (result.hasErrors()) {
                return "user/user";
            } else {
                userService.update(userId, user);
                return "redirect:/users";
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            LOGGER.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "user/user";
        }
    }

    @GetMapping(value = {"/{id}/delete"})
    public String showDeleteUserById(Model model, @PathVariable UUID id) {
        UserDto user = null;
        model.addAttribute("roles", init());
        try {
            user = userService.find(id);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "User not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("user", user);
        return "user/user-delete";
    }

    @PostMapping(value = {"/{id}/delete"})
    public String deleteUserById(Model model, @PathVariable UUID id) {
        model.addAttribute("roles", init());
        try {
            userService.deleteById(id);
            return "redirect:/users";
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            LOGGER.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "user/user-delete";
        }
    }
}