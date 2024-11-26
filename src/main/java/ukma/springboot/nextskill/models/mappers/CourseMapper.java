package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import static ukma.springboot.nextskill.models.mappers.MapperUtility.mapIfInitialized;
import static ukma.springboot.nextskill.models.mappers.MapperUtility.orElse;

public class CourseMapper {

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
        return CourseResponse.builder()
                .uuid(courseEntity.getUuid())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .createdAt(courseEntity.getCreatedAt())
                .teacher(UserMapper.toUserResponse(courseEntity.getTeacher()))
                .students(mapIfInitialized(courseEntity.getStudents(), UserMapper::toUserResponse))
                .sections(mapIfInitialized(courseEntity.getSections(), SectionMapper::toSectionResponse))
                .build();
    }
}
