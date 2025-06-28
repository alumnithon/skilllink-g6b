package alumnithon.skilllink.domain.shared.validator;

import alumnithon.skilllink.domain.shared.interfaces.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (anEnum == null) {
            return true; // Si el valor es nulo, se considera válido
        }
        return Arrays.asList(enumClass.getEnumConstants()).contains(anEnum);
    }
}
