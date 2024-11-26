package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

import static ukma.springboot.nextskill.models.mappers.MapIfInitialized.mapIfInitialized;

public class CourseMapper {

    public static CourseEntity toCourseEntity(CourseView courseView) {
        return CourseEntity.builder()
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
