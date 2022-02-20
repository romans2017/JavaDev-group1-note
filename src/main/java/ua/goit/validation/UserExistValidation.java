package ua.goit.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UserExistValidator.class)
public @interface UserExistValidation {

    String message() default "Users are exist with this role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
