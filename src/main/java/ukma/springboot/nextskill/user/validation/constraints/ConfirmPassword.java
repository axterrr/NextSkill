package ukma.springboot.nextskill.user.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.modulith.NamedInterface;
import ukma.springboot.nextskill.user.validation.constraints.validators.ConfirmPasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NamedInterface
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
