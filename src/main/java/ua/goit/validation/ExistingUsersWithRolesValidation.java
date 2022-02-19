package ua.goit.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistingUsersWithRolesValidator.class)
public @interface ExistingUsersWithRolesValidation {
    String message() default "This role is using in users!!!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
