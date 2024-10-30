package ukma.springboot.nextskill.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ukma.springboot.nextskill.validation.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Password must contain at least 8 symbols, upper and lower letters, digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
