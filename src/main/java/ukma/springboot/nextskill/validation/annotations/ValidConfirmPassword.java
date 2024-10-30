package ukma.springboot.nextskill.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ukma.springboot.nextskill.validation.ConfirmPasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidConfirmPassword {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
