package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.model.Course;

public class CourseMapper {
    private CourseMapper() {}

    public static Course toCourse(CourseEntity courseEntity) {
        if (courseEntity == null) {
            return null;
        }

        Course course = new Course(courseEntity.getUuid(), courseEntity.getCreationDate());
        course.setName(courseEntity.getName());
        course.setDescription(courseEntity.getDescription());
        course.setTeacher(UserMapper.toUser(courseEntity.getTeacher()));
        return course;
    }

    public static CourseEntity toCourseEntity(Course course) {
        if (course == null) {
            return null;
        }

        CourseEntity courseEntity = new CourseEntity(course.getUuid(), course.getCreationDate());
        courseEntity.setName(course.getName());
        courseEntity.setDescription(course.getDescription());
        courseEntity.setTeacher(UserMapper.toUserEntity(course.getTeacher()));
        return courseEntity;
    }
}
