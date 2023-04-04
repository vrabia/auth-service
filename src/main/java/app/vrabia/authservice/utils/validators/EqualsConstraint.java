package app.vrabia.authservice.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EqualsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualsConstraint {
    String message() default "Field don't match";
    String compareWith();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
