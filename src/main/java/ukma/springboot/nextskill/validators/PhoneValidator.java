package ukma.springboot.nextskill.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;

@Component()
public class PhoneValidator implements Validator {

    private static final String PHONE_PATTERN = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    private static final Pattern pattern = Pattern.compile(PHONE_PATTERN);

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String phoneNumber = (String) target;

        if (phoneNumber.trim().isEmpty()) {
            errors.rejectValue("phone", "phone.empty", "Phone number cannot be empty");
            return;
        }

        if (!pattern.matcher(phoneNumber).matches()) {
            errors.rejectValue("phone", "phone.invalid", "Invalid phone number format");
        }
    }
}
