package ua.goit.validation.deleteRole;

import lombok.RequiredArgsConstructor;
import ua.goit.users.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@RequiredArgsConstructor
public class UserExistValidator implements ConstraintValidator<UserExistValidation, UUID> {

    private final UserService userService;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext cont) {
        return !userService.isExistByRoleId(value);
    }
}
