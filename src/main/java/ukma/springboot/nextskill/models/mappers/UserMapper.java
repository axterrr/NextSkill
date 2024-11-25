package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.views.UserView;

public class UserMapper {

    public static UserEntity toUserEntity(UserView userView) {
        return UserEntity.builder()
                .username(userView.getUsername())
                .name(userView.getName())
                .surname(userView.getSurname())
                .email(userView.getEmail())
                .phone(userView.getPhone())
                .description(userView.getDescription())
                .role(userView.getRole())
                .passwordHash(userView.getPassword())
                .build();
    }
}
