package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.UnknownUser;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserEntity getUserByUsername(String username) throws UnknownUser{
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) throw new UnknownUser("There is no user with "+ username +" username present.");
        return optional.get();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
