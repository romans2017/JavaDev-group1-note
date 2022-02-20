package ua.goit.validation.unique;

import ua.goit.base.BaseDto;
import ua.goit.base.BaseEntity;
import ua.goit.base.BaseService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.UUID;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface UniqueValidation {

    String message() default "This object is not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends BaseService<? extends BaseEntity<UUID>, ? extends BaseDto>> classService();
}
