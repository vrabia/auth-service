package app.vrabia.authservice.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EqualsValidator implements ConstraintValidator<EqualsConstraint, String> {
    private String compareWith;

    @Override
    public void initialize(EqualsConstraint compare) {
        this.compareWith = compare.compareWith();
    }

    @Override
    public boolean isValid(String toCompare, ConstraintValidatorContext cxt) {
        return toCompare != null && toCompare.equals(compareWith);
    }
}