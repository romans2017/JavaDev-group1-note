package ua.goit.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ua.goit.base.BaseDto;
import ua.goit.notes.Note;
import ua.goit.notes.NoteDto;
import ua.goit.roles.RoleDto;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
//@UniqueValidation(classService = UserService.class, groups = {OnCreate.class, OnUpdate.class})
public class UserDto implements BaseDto {

    private UUID id;

    @Size(min = 5, message = "User name should be at least 5 character.")
    private String userName;

    @NotBlank
    @Length(min = 5, message = "Password should be at least 5 character.")
    private String password;

    @NotNull(message = "User has minimum one role!")
    private List<RoleDto> roles = new ArrayList<>();

    private List<NoteDto> notes;
}
