package ua.goit.roles;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.goit.base.BaseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
//@UniqueValidation(classService = RoleService.class)
public class RoleDto implements BaseDto {

    private UUID id;

    @NotBlank
    @Size(min=3, message = "Role should be at least 3 character.")
    private String name;
}
