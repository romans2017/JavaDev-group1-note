package ua.goit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ua.goit.users.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UserExistValidator implements ConstraintValidator<UserExistValidation, UUID> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext cont) {
        return !userService.isExistByRoleId(value);
    }
}
