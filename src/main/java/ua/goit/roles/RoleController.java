package ua.goit.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@PreAuthorize("hasAuthority('admin')")
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("roles", roleService.findAll());
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
            return "role/role";
        }
        roleService.update(role.getId(), role);
        return "redirect:/roles";
    }

    @GetMapping("/{id}")
    public String showEdit(@PathVariable UUID id, Model model) {
        model.addAttribute("add", false);
        model.addAttribute("role", roleService.find(id));
        return "role/role";
    }

    @PostMapping("/{id}")
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
    public String removeRole(@PathVariable(value = "id") UUID id) {
        roleService.delete(id);
        return "redirect:/roles";
    }
}