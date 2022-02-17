package ua.goit.roles;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goit.exception.BadResourceException;
import ua.goit.exception.ResourceAlreadyExistsException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
//@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/roles")
public class RoleController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleService service;

    private List<RoleDto> init() {
        return service.findAll();
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("roles", init());
        return "role/roles";
    }

    @GetMapping("/add")
    public String showCreate(Model model) {
        RoleDto role = new RoleDto();
        model.addAttribute("add", true);
        model.addAttribute("role", role);
        return "role/role";
    }

    @PostMapping(value = "/add")
    public String addRole(Model model, @ModelAttribute("role") @Valid RoleDto role,
                          BindingResult result) throws BadResourceException, ResourceAlreadyExistsException {
        model.addAttribute("add", true);
        if (result.hasErrors()) {
            return "role/role";
        } else {
            service.update(role);
            return "redirect:/roles";
        }
    }

    @GetMapping("/{id}")
    public String showEdit(@PathVariable UUID id, Model model) throws ResourceAlreadyExistsException {
        model.addAttribute("role", service.find(id));
        return "role/role";
    }

    @PostMapping(value = {"/{id}"})
    public String updateRole(Model model, @PathVariable UUID id, @ModelAttribute("role") @Valid RoleDto role,
                             BindingResult result) {
        boolean isExistByName = service.existsByName(role.getName());
        try {
            if (result.hasErrors() || isExistByName) {
                if (isExistByName) {
                    model.addAttribute("errorUniqueRole", "This role is exists! Role must be unique!");
                    return "role/role";
                }
                return "role/role";
            } else {
                service.update(id, role);
                return "redirect:/roles";
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            LOGGER.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "role/role";
        }
    }

    @GetMapping(value = {"/{id}/delete"})
    public String showDeleteRoleById(
            Model model, @PathVariable UUID id) {
        RoleDto role = null;
        try {
            role = service.find(id);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Role not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("role", role);
        return "role/role-delete";
    }

    @PostMapping(value = {"/{id}/delete"})
    public String deleteRoleById(
            Model model, @PathVariable UUID id) {
        try {
            service.deleteById(id);
            return "redirect:/roles";
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            LOGGER.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "role/role-delete";
        }
    }
}