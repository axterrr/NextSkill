package ukma.springboot.nextskill.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ukma.springboot.nextskill.model.dto.UserDto;
import ukma.springboot.nextskill.validation.annotations.ValidConfirmPassword;

public class ConfirmPasswordValidator implements ConstraintValidator<ValidConfirmPassword, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}
