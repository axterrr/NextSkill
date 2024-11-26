package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.responses.CourseResponse;
import ukma.springboot.nextskill.models.views.CourseView;

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
                .students(courseEntity.getStudents().stream().map(UserMapper::toUserResponse).toList())
                .sections(courseEntity.getSections().stream().map(SectionMapper::toSectionResponse).toList())
                .build();
    }
}
