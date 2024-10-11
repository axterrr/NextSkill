package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.dto.CourseDto;
import ukma.springboot.nextskill.entities.CourseEntity;
import ukma.springboot.nextskill.model.Course;

public class CourseMapper {
    private CourseMapper() {}

    public static Course toCourse(CourseEntity courseEntity) {
        if (courseEntity == null) return null;

        Course course = new Course();
        course.setUuid(courseEntity.getUuid());
        course.setCreationDate(courseEntity.getCreationDate());
        course.setName(courseEntity.getName());
        course.setDescription(courseEntity.getDescription());
        course.setTeacher(UserMapper.toUser(courseEntity.getTeacher()));

        return course;
    }

    public static CourseEntity toCourseEntity(Course course) {
        if (course == null) return null;

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setUuid(course.getUuid());
        courseEntity.setCreationDate(course.getCreationDate());
        courseEntity.setName(course.getName());
        courseEntity.setDescription(course.getDescription());
        courseEntity.setTeacher(UserMapper.toUserEntity(course.getTeacher()));
        return courseEntity;
    }

    public static CourseDto toCourseDto(Course course) {
        if (course == null) return null;

        CourseDto courseDto = new CourseDto();
        courseDto.setUuid(course.getUuid());
        courseDto.setCreationDate(course.getCreationDate());
        courseDto.setName(course.getName());
        courseDto.setDescription(course.getDescription());
        courseDto.setTeacher(UserMapper.toUserDto(course.getTeacher()));
        return courseDto;
    }

    public static Course toCourse(CourseDto courseDto) {
        if (courseDto == null) return null;

        Course course = new Course();
        course.setUuid(courseDto.getUuid());
        course.setCreationDate(courseDto.getCreationDate());
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setTeacher(UserMapper.toUser(courseDto.getTeacher()));

        return course;
    }
}
