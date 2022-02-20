package ua.goit.validation.deleteAdmin;

import ua.goit.base.BaseDto;
import ua.goit.base.BaseEntity;
import ua.goit.base.BaseService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.UUID;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NonAdminValidator.class)
public @interface NonAdminValidation {

    String message() default "Admin can't be deleted!!!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends BaseService<? extends BaseEntity<UUID>, ? extends BaseDto>> classService();
}
