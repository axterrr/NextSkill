package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.views.CourseView;

public class CourseMapper {

    public static CourseEntity toCourseEntity(CourseView courseView) {
        return CourseEntity.builder()
                .name(courseView.getName())
                .description(courseView.getDescription())
                .teacher(UserEntity.builder().uuid(courseView.getTeacherId()).build())
                .build();
    }
}
