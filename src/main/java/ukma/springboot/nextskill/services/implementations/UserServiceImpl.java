package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.mappers.UserMapper;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.services.UserService;
import ukma.springboot.nextskill.validation.UserValidator;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserValidator userValidator;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse get(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
        return UserMapper.toUserResponse(userEntity);
    }

    @Override
    public UserResponse create(UserView userView) {
        userValidator.validateForCreation(userView);
        UserEntity userEntity = userRepository.save(UserMapper.toUserEntity(userView, passwordEncoder));
        return UserMapper.toUserResponse(userEntity);
    }

    @Override
    public UserResponse update(UserView userView) {
        userValidator.validateForUpdate(userView);
        UserEntity existingUser = userRepository.findById(userView.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("User", userView.getUuid()));
        UserEntity userEntity = userRepository.save(UserMapper.toUserEntity(userView, existingUser, passwordEncoder));
        return UserMapper.toUserResponse(userEntity);
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", UUID.randomUUID()));
    }
}
