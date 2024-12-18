package ukma.springboot.nextskill.models.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;

import static ukma.springboot.nextskill.models.mappers.MapperUtility.orElse;
import static ukma.springboot.nextskill.models.mappers.MapperUtility.mapIfInitialized;

public class UserMapper {

    private UserMapper() {}

    public static UserEntity toUserEntity(UserView userView, UserEntity userEntity, PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .uuid(userEntity.getUuid())
                .username(orElse(userView.getUsername(), userEntity.getUsername()))
                .name(orElse(userView.getName(), userEntity.getName()))
                .surname(orElse(userView.getSurname(), userEntity.getSurname()))
                .email(orElse(userView.getEmail(), userEntity.getEmail()))
                .phone(orElse(userView.getPhone(), userEntity.getPhone()))
                .description(orElse(userView.getDescription(), userEntity.getDescription()))
                .isDisabled(orElse(userView.getIsDisabled(), userEntity.isDisabled()))
                .passwordHash(userView.getPassword() == null ? userEntity.getPasswordHash() : passwordEncoder.encode(userView.getPassword()))
                .ownCourses(userEntity.getOwnCourses())
                .courses(userEntity.getCourses())
                .build();
    }

    public static UserEntity toUserEntity(UserView userView, PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .uuid(userView.getUuid())
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
        if (userEntity == null) { return null; }
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
                .ownCourses(mapIfInitialized(userEntity.getOwnCourses(), CourseMapper::toCourseResponseWithoutTeacher))
                .courses(mapIfInitialized(userEntity.getCourses(), CourseMapper::toCourseResponse))
                .build();
    }
}
