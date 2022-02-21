package ua.goit.roles;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.goit.validation.deleteAdmin.NonAdminValidation;
import ua.goit.validation.deleteRole.UserExistValidation;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Controller
@PreAuthorize("hasAuthority('admin')")
@RequestMapping("roles")
@Validated
public class RoleController {

   RoleService roleService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("errorConstraint", null);
        return "role/roles";
    }

    @GetMapping("add")
    public String showCreate(Model model) {
        RoleDto role = new RoleDto();
        model.addAttribute("add", true);
        model.addAttribute("role", role);
        return "role/role";
    }

    @PostMapping("add")
    public String addRole(Model model, @ModelAttribute("role") @Validated RoleDto role,
                          BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("add", true);
            model.addAttribute("allRoles", roleService.findAll());
            model.addAttribute("role", role);
            return "role/role";
        }
        roleService.create(role);
        return "redirect:/roles";
    }

    @GetMapping("{id}")
    public String showEdit(@PathVariable UUID id, Model model) {
        model.addAttribute("add", false);
        model.addAttribute("role", roleService.find(id));
        return "role/role";
    }

    @PostMapping("{id}")
    public String updateRole(Model model, @PathVariable UUID id, @ModelAttribute("role") @Validated RoleDto role,
                             BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("add", false);
            model.addAttribute("allRoles", roleService.findAll());
            return "role/role";
        }
        roleService.update(id, role);
        return "redirect:/roles";
    }

    @GetMapping("remove_role/{id}")
    public String removeRole(@PathVariable(value = "id") @UserExistValidation @NonAdminValidation(classService = RoleService.class) UUID id)
            throws ConstraintViolationException {
        roleService.delete(id);
        return "redirect:/roles";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(
            Exception ex, Model model) {
        model.addAttribute("errorConstraint", ex.getMessage());
        model.addAttribute("roles", roleService.findAll());
        return "role/roles";
    }
}