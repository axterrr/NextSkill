package ukma.springboot.nextskill.user;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserInternalAPI {
    List<UserEntity> getAll();
}
