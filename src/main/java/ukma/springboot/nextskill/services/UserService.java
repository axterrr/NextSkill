package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.interfaces.IUserService;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.validators.PhoneValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private PhoneValidator phoneValidator;

    @Override
    public User getUser(UUID id) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty()) throw new IllegalArgumentException("User not found with id: " + id);

        return UserMapper.toUser(result.get());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream().map(UserMapper::toUser).toList();
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {
        Errors validationErrors = new BeanPropertyBindingResult(updatedUser.getPhone(), "phone");
        phoneValidator.validate(updatedUser.getPhone(), validationErrors);

        if (validationErrors.hasErrors()) throw new IllegalArgumentException("User's phone is bot valid, id = " + id);

        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) throw new IllegalArgumentException("User not found with id: " + id);

        UserEntity existingUserEntity = existingUser.get();

        existingUserEntity.setUsername(updatedUser.getUsername());
        existingUserEntity.setName(updatedUser.getName());
        existingUserEntity.setSurname(updatedUser.getSurname());
        existingUserEntity.setEmail(updatedUser.getEmail());
        existingUserEntity.setPhone(updatedUser.getPhone());
        existingUserEntity.setAvatarLink(updatedUser.getAvatarLink());
        existingUserEntity.setDescription(updatedUser.getDescription());
        existingUserEntity.setUserRole(updatedUser.getUserRole());
        existingUserEntity.setDisabled(updatedUser.isDisabled());

        return UserMapper.toUser(userRepository.save(existingUserEntity));
    }

    @Override
    public void deleteUser(UUID id) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty()) throw new IllegalArgumentException("User not found with id: " + id);

        userRepository.deleteById(id);
    }

    //Setter wiring
    @Autowired
    private void setPhoneValidator(PhoneValidator phoneValidator) {
        this.phoneValidator = phoneValidator;
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
