package ua.goit.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseDto;
import ua.goit.roles.RoleDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
public class UserDto implements BaseDto {

    private UUID id;

    @Length(min = 5, message = "User name should be at least 5 character.")
    private String name;

    @NotBlank
    @Length(min = 5, message = "Password should be at least 5 character.")
    private String password;

    @NotEmpty(message = "User has minimum one role!")
    private List<RoleDto> roles = new ArrayList<>();
}
