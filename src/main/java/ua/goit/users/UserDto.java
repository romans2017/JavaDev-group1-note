package ua.goit.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseDto;
import ua.goit.roles.RoleDto;
import ua.goit.validation.unique.OnCreate;
import ua.goit.validation.unique.OnUpdate;
import ua.goit.validation.unique.UniqueValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode
@UniqueValidation(classService = UserService.class, groups = {OnCreate.class, OnUpdate.class})
public class UserDto implements BaseDto {

    private UUID id;

    @Pattern(regexp = "[a-zA-Z0-9]*",groups = {OnCreate.class,OnUpdate.class},message = "User name should be alphanumeric")
    @Length(groups = {OnCreate.class, OnUpdate.class}, min = 5, max = 50, message = "User name should be at least 5 character and maximum length of 50.")
    private String name;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Length(groups = {OnCreate.class, OnUpdate.class}, min = 8, max = 100, message = "Password should be at least 8 character and maximum length of 100.")
    @EqualsAndHashCode.Exclude
    private String password;

    @NotEmpty(groups = {OnUpdate.class}, message = "User has minimum one role!")
    private List<RoleDto> roles = new ArrayList<>();
}
