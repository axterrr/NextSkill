package ukma.springboot.nextskill.core.validation.constraints.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ukma.springboot.nextskill.core.models.views.UserView;
import ukma.springboot.nextskill.core.validation.constraints.ConfirmPassword;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, UserView> {
    @Override
    public boolean isValid(UserView userView, ConstraintValidatorContext context) {
        return userView.getPassword() == null || userView.getConfirmPassword() == null
                || userView.getPassword().equals(userView.getConfirmPassword());
    }
}
