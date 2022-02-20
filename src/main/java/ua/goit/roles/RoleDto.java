package ua.goit.roles;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseDto;
import ua.goit.validation.unique.UniqueValidation;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@UniqueValidation(classService = RoleService.class)
public class RoleDto implements BaseDto {

    private UUID id;

    @NotBlank
    @Length(min=3, message = "Role should be at least 3 character.")
    private String name;
}