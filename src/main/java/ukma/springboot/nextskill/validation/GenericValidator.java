package ukma.springboot.nextskill.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

public class GenericValidator<T> {

    @Autowired
    private Validator validator;

    public void validateForCreation(T view) {
        Set<ConstraintViolation<T>> violations = validator.validate(view);
        if (!violations.isEmpty()) {
            throwException(violations);
        }
    }

    public void validateForUpdate(T view) {
        Set<ConstraintViolation<T>> violations = validator.validate(view);
        violations = violations.stream()
                .filter(violation -> violation.getInvalidValue() != null)
                .collect(Collectors.toSet());
        if (!violations.isEmpty()) {
            throwException(violations);
        }
    }

    private void throwException(Set<ConstraintViolation<T>> violations) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
        }
        throw new ValidationException(errorMessage.toString());
    }
}
