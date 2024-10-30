package ukma.springboot.nextskill.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.model.dto.UserDto;
import ukma.springboot.nextskill.model.entities.UserEntity;
import ukma.springboot.nextskill.exceptions.DuplicateUniqueFieldException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.services.interfaces.IUserService;
import ukma.springboot.nextskill.model.pojo.User;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger userLogger = LoggerFactory.getLogger(UserService.class);

    @Override
    public User getUser(UUID id) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty()) throw new ResourceNotFoundException("User", id.toString());
        return UserMapper.toUser(result.get());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUser).toList();
    }

    @Override
    public User createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto, passwordEncoder);
        checkUniqueFields(user, null);
        UserEntity userEntity = UserMapper.toUserEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        userLogger.info("Created user {} with id {}", savedEntity.getUsername(), savedEntity.getUuid());
        return UserMapper.toUser(savedEntity);
    }

    @Override
    public User updateUser(UUID id, UserDto userDto) {
        User updatedUser = UserMapper.toUser(userDto, passwordEncoder);
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) throw new ResourceNotFoundException("User", id.toString());
        userLogger.info("Received new user data for id {}, data: {}", updatedUser.getUuid(), updatedUser);
        userLogger.info("Current user data for id {}, data: {}", existingUser.get().getUuid(), existingUser.get());
        checkUniqueFields(updatedUser, existingUser.get());
        updatedUser.setUuid(existingUser.get().getUuid());
        UserEntity result = userRepository.save(UserMapper.toUserEntity(updatedUser));
        userLogger.info("Updated user {} with id {}", result.getUsername(), result.getUuid());
        return UserMapper.toUser(result);
    }

    @Override
    public void deleteUser(UUID id) {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isEmpty()) throw new ResourceNotFoundException("User", id.toString());
        userLogger.info("User {} with id {} was deleted", result.get().getUsername(), result.get().getUuid());
        userRepository.deleteById(id);
    }

    @Override
    public void processUser(UserDto userDto) {
        Optional<UserEntity> result = userRepository.findById(userDto.getUuid());
        if (result.isEmpty()) {
            createUser(userDto);
        } else {
            updateUser(userDto.getUuid(), userDto);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<UserEntity> result = userRepository.findByUsername(username);
        if (result.isEmpty()) throw new ResourceNotFoundException("User", "-");
        return UserMapper.toUser(result.get());
    }

    private void checkUniqueFields(User user, UserEntity existingUser) {
        if (existingUser == null || !existingUser.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new DuplicateUniqueFieldException("User", "username", user.getUsername());
            }
        }

        if (existingUser == null || !existingUser.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateUniqueFieldException("User", "email", user.getEmail());
            }
        }

        if (existingUser == null || !existingUser.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(user.getPhone())) {
                throw new DuplicateUniqueFieldException("User", "phone", user.getPhone());
            }
        }
    }

}
