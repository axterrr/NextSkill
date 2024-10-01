package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.dto.UserDto;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.model.User;

public class UserMapper {

    private UserMapper() {}

    public static User toUser(UserEntity userEntity) {
        if (userEntity == null) return null;

        User user = new User();
        user.setUuid(userEntity.getUuid());
        user.setCreatedAt(userEntity.getCreatedAt());
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
        if (user == null) return null;

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

    public static UserDto toUserDto(User user) {
        if (user == null) return null;

        UserDto userDto = new UserDto();
        userDto.setUuid(user.getUuid());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setAvatarLink(user.getAvatarLink());
        userDto.setDescription(user.getDescription());
        userDto.setUserRole(user.getUserRole());
        userDto.setDisabled(user.isDisabled());

        return userDto;
    }

    public static User toUser(UserDto userDto) {
        if (userDto == null) return null;

        User user = new User();
        user.setUuid(userDto.getUuid());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setAvatarLink(userDto.getAvatarLink());
        user.setDescription(userDto.getDescription());
        user.setUserRole(userDto.getUserRole());
        user.setDisabled(userDto.isDisabled());

        return user;
    }
}

