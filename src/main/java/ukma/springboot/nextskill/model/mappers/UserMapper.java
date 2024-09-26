package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.model.User;

public class UserMapper {

    private UserMapper() {}

    public static User toUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User(userEntity.getUuid(), userEntity.getCreatedAt());
        user.setUsername(userEntity.getUsername());
        user.setName(userEntity.getName());
        user.setSurname(userEntity.getSurname());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setAvatarLink(userEntity.getAvatarLink());
        user.setDescription(userEntity.getDescription());
        user.setUserRole(userEntity.getUserRole());
        user.setDisabled(userEntity.isDisabled());

        return user;
    }

    public static UserEntity toUserEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity(user.getUuid(), user.getCreatedAt());
        userEntity.setUsername(user.getUsername());
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setAvatarLink(user.getAvatarLink());
        userEntity.setDescription(user.getDescription());
        userEntity.setUserRole(user.getUserRole());
        userEntity.setDisabled(user.isDisabled());

        return userEntity;
    }
}

