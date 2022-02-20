package ua.goit.validation.unique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ua.goit.base.BaseDto;
import ua.goit.base.BaseService;
import ua.goit.validation.unique.UniqueValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<UniqueValidation, BaseDto> {

    private BaseService<?, ?> modelService;

    @Autowired
    private ApplicationContext context;

    @Override
    public void initialize(UniqueValidation constraintAnnotation) {
        modelService = context.getBean(constraintAnnotation.classService());
    }

    @Override
    public boolean isValid(BaseDto value, ConstraintValidatorContext cont) {
        return !modelService.isExist(value);
    }
}
