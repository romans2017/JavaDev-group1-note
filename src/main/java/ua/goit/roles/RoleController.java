package ua.goit.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAuthority('admin')")
@RequestMapping("roles")
public class RoleController {

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

  @GetMapping("add")
  public String showCreate(Model model) {
    RoleDto role = new RoleDto();
    model.addAttribute("add", true);
    model.addAttribute("role", role);
    return "role/role";
  }

  @PostMapping("add")
  public String addRole(Model model, @ModelAttribute("role") @Valid RoleDto role,
                        BindingResult result) throws BadResourceException, ResourceAlreadyExistsException {
    boolean isExistByName = service.existsByName(role.getName());
    model.addAttribute("add", true);
    if (result.hasErrors() || isExistByName) {
      if (isExistByName) {
        model.addAttribute("errorUniqueRole", "This role is exists! Role must be unique!");
        return "role/role";
      }
      return "role/role";
    } else {
      service.save(role);
      return "redirect:/roles";
    }
  }

  @GetMapping("/{id}")
  public String showEdit(@PathVariable UUID id, Model model) throws ResourceAlreadyExistsException {
    model.addAttribute("role", service.find(id));
    return "role/role";
  }

  @PostMapping("/{id}")
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
      model.addAttribute("errorMessage", errorMessage);
      model.addAttribute("add", false);
      return "role/role";
    }
  }

  @GetMapping("remove_role/{id}")
  public String removeRole(@PathVariable(value = "id") UUID id) {
    service.delete(id);
    return "redirect:/roles";
  }
}