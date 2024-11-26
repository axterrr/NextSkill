package ukma.springboot.nextskill.models.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;

import static ukma.springboot.nextskill.models.mappers.MapIfInitialized.mapIfInitialized;

public class UserMapper {

    public static UserEntity toUserEntity(UserView userView, PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .username(userView.getUsername())
                .name(userView.getName())
                .surname(userView.getSurname())
                .email(userView.getEmail())
                .phone(userView.getPhone())
                .description(userView.getDescription())
                .role(userView.getRole())
                .passwordHash(passwordEncoder.encode(userView.getPassword()))
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
                .ownCourses(mapIfInitialized(userEntity.getOwnCourses(), CourseMapper::toCourseResponse))
                .courses(mapIfInitialized(userEntity.getCourses(), CourseMapper::toCourseResponse))
                .build();
    }
}
