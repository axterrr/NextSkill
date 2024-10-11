package ukma.springboot.nextskill.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ukma.springboot.nextskill.validation.VerifiedEmailValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VerifiedEmailValidator.class)
public @interface VerifiedEmail {
    String message() default "Invalid Email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
