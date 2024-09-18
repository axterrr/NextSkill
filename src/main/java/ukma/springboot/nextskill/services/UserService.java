package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ukma.springboot.nextskill.interfaces.IUserService;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.validators.PhoneValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    // Field wiring
    @Autowired
    private MockDatabaseUserService mockDatabaseUserService;

    private PhoneValidator phoneValidator;

    @Override
    public User getUser(UUID id) {
        Optional<User> result = mockDatabaseUserService.find(id);
        return result.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return mockDatabaseUserService.getAll();
    }

    @Override
    public boolean updateUser(UUID id, User updatedUser) {
        Errors validationErrors = new BeanPropertyBindingResult(updatedUser.getPhone(), "phone");
        phoneValidator.validate(updatedUser.getPhone(), validationErrors);

        if (validationErrors.hasErrors()) return false;

        mockDatabaseUserService.set(id, updatedUser);
        return true;
    }

    @Override
    public boolean deleteUser(UUID id) {
        mockDatabaseUserService.delete(id);
        return true;
    }

    //Setter wiring
    @Autowired
    private void setPhoneValidator(PhoneValidator phoneValidator) {
        this.phoneValidator = phoneValidator;
    }
}
