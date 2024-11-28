package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import static ukma.springboot.nextskill.models.mappers.MapperUtility.mapIfInitialized;
import static ukma.springboot.nextskill.models.mappers.MapperUtility.orElse;

public class CourseMapper {

    private CourseMapper() {}

    public static CourseEntity toCourseEntity(CourseView courseView, CourseEntity courseEntity) {
        return CourseEntity.builder()
                .uuid(courseEntity.getUuid())
                .name(orElse(courseView.getName(), courseEntity.getName()))
                .description(orElse(courseView.getDescription(), courseEntity.getDescription()))
                .teacher(courseEntity.getTeacher())
                .students(courseEntity.getStudents())
                .sections(courseEntity.getSections())
                .build();
    }

    public static CourseEntity toCourseEntity(CourseView courseView) {
        return CourseEntity.builder()
                .uuid(courseView.getUuid())
                .name(courseView.getName())
                .description(courseView.getDescription())
                .teacher(UserEntity.builder().uuid(courseView.getTeacherId()).build())
                .build();
    }

    public static CourseResponse toCourseResponse(CourseEntity courseEntity) {
        if (courseEntity == null) { return null; }
        return CourseResponse.builder()
                .uuid(courseEntity.getUuid())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .createdAt(courseEntity.getCreatedAt())
                .teacher(UserMapper.toUserResponse(courseEntity.getTeacher()))
                .students(mapIfInitialized(courseEntity.getStudents(), UserMapper::toUserResponse))
                .sections(mapIfInitialized(courseEntity.getSections(), SectionMapper::toSectionResponseWithoutCourse))
                .build();
    }

    public static CourseResponse toCourseResponseWithoutTeacher(CourseEntity courseEntity) {
        if (courseEntity == null) { return null; }
        return CourseResponse.builder()
                .uuid(courseEntity.getUuid())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .createdAt(courseEntity.getCreatedAt())
                .students(mapIfInitialized(courseEntity.getStudents(), UserMapper::toUserResponse))
                .sections(mapIfInitialized(courseEntity.getSections(), SectionMapper::toSectionResponseWithoutCourse))
                .build();
    }
    public static CourseEntity toCourseEntity(CourseResponse courseResponse) {
        return CourseEntity.builder()
                .uuid(courseResponse.getUuid())
                .name(courseResponse.getName())
                .description(courseResponse.getDescription())
                .teacher(UserEntity.builder().uuid(courseResponse.getTeacher().getUuid()).build())
                .students(courseResponse.getStudents().stream()
                        .map(student -> UserEntity.builder().uuid(student.getUuid()).build())
                        .toList())
                .sections(courseResponse.getSections().stream()
                        .map(section -> SectionEntity.builder().uuid(section.getUuid()).build())
                        .toList())
                .build();
    }
}
