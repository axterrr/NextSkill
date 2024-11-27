package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;

public interface UserService extends GenericService<UserView, UserResponse> {
    UserEntity getUserByUsername(String username);
    UserResponse getAuthenticatedUser();
}
