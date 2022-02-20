package ua.goit.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseDto;
import ua.goit.roles.RoleDto;
import ua.goit.validation.unique.OnCreate;
import ua.goit.validation.unique.OnUpdate;
import ua.goit.validation.unique.UniqueValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@UniqueValidation(classService = UserService.class, groups = {OnCreate.class, OnUpdate.class})
public class UserDto implements BaseDto {

    private UUID id;

    @Length(groups = {OnCreate.class, OnUpdate.class}, min = 5, message = "User name should be at least 5 character.")
    private String name;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Length(groups = {OnCreate.class, OnUpdate.class}, min = 5, message = "Password should be at least 5 character.")
    private String password;

    @NotEmpty(groups = {OnUpdate.class}, message = "User has minimum one role!")
    private List<RoleDto> roles = new ArrayList<>();
}
