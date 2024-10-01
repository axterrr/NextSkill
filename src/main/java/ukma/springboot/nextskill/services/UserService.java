package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.exceptions.DuplicateUniqueFieldException;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.interfaces.IUserService;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String id) {
        Optional<UserEntity> result = userRepository.findById(UUID.fromString(id));
        if (result.isEmpty()) throw new ResourceNotFoundException("User", id);
        return UserMapper.toUser(result.get());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream().map(UserMapper::toUser).toList();
    }

    @Override
    public User createUser(User user) {
        checkUniqueFields(user, null);
        UserEntity userEntity = UserMapper.toUserEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return UserMapper.toUser(savedEntity);
    }

    @Override
    public User updateUser(String id, User updatedUser) {
        Optional<UserEntity> existingUser = userRepository.findById(UUID.fromString(id));
        if (existingUser.isEmpty()) throw new ResourceNotFoundException("User", id);

        UserEntity existingUserEntity = existingUser.get();
        checkUniqueFields(updatedUser, existingUser.get());

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
    public void deleteUser(String id) {
        Optional<UserEntity> result = userRepository.findById(UUID.fromString(id));
        if (result.isEmpty()) throw new ResourceNotFoundException("User", id);

        userRepository.deleteById(UUID.fromString(id));
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
