package ua.goit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ua.goit.users.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ExistingUsersWithRolesValidator implements ConstraintValidator<ExistingUsersWithRolesValidation, UUID> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        return !userService.isExistByRoleId(value);
    }
}
