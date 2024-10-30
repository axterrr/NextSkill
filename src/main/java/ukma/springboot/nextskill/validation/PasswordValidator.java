package ukma.springboot.nextskill.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ukma.springboot.nextskill.validation.annotations.Password;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$");
    }
}
