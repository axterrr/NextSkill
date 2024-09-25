package ukma.springboot.nextskill.services;

import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.interfaces.IMockDatabaseService;
import ukma.springboot.nextskill.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MockDatabaseUserService implements IMockDatabaseService<User, UUID> {

    @Override
    public Optional<User> find(UUID Id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public void set(UUID Id, User object) {
        System.out.println("It changed.");
    }

    @Override
    public void delete(UUID Id) {
        System.out.println("It deleted.");
    }
}
