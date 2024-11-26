package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.UserResponse;
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

    public static UserResponse toUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .uuid(userEntity.getUuid())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .description(userEntity.getDescription())
                .createdAt(userEntity.getCreatedAt())
                .role(userEntity.getRole())
                .isDisabled(userEntity.isDisabled())
                .ownCourses(userEntity.getOwnCourses().stream().map(CourseMapper::toCourseResponse).toList())
                .courses(userEntity.getCourses().stream().map(CourseMapper::toCourseResponse).toList())
                .build();
    }
}
