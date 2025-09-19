package ukma.springboot.nextskill.user.validation;

import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.common.models.views.UserView;
import ukma.springboot.nextskill.common.validation.GenericValidator;

@Component
public class UserValidator extends GenericValidator<UserView> {
}
