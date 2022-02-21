package ua.goit.validation.deleteAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ua.goit.base.BaseService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.UUID;

public class NonAdminValidator implements ConstraintValidator<NonAdminValidation, UUID> {

    private BaseService<?, ?> modelService;

    @Autowired
    private ApplicationContext context;

    @Override
    public void initialize(NonAdminValidation constraintAnnotation) {
        modelService = context.getBean(constraintAnnotation.classService());
    }

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext cont) {
        return !modelService.find(value)
                .getName().toLowerCase(Locale.ROOT)
                .equals("admin");
    }
}
