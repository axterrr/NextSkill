package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.dto.CourseSectionDto;
import ukma.springboot.nextskill.entities.CourseSectionEntity;
import ukma.springboot.nextskill.model.CourseSection;

public class CourseSectionMapper {

    private CourseSectionMapper() {}

    public static CourseSection toCourseSection(CourseSectionEntity courseSectionEntity) {
        if (courseSectionEntity == null) return null;

        CourseSection courseSection = new CourseSection();
        courseSection.setUuid(courseSectionEntity.getUuid());
        courseSection.setName(courseSectionEntity.getName());
        courseSection.setDescription(courseSectionEntity.getDescription());
        courseSection.setCourse(CourseMapper.toCourse(courseSectionEntity.getCourse()));

        return courseSection;
    }

    public static CourseSectionEntity toCourseSectionEntity(CourseSection courseSection) {
        if (courseSection == null) return null;

        CourseSectionEntity courseSectionEntity = new CourseSectionEntity();
        courseSectionEntity.setUuid(courseSection.getUuid());
        courseSectionEntity.setName(courseSection.getName());
        courseSectionEntity.setDescription(courseSection.getDescription());
        courseSectionEntity.setCourse(CourseMapper.toCourseEntity(courseSection.getCourse()));
        return courseSectionEntity;
    }

    public static CourseSectionDto toCourseSectionDto(CourseSection courseSection) {
        if (courseSection == null) return null;

        CourseSectionDto courseSectionDto = new CourseSectionDto();
        courseSectionDto.setUuid(courseSection.getUuid());
        courseSectionDto.setName(courseSection.getName());
        courseSectionDto.setDescription(courseSection.getDescription());
        courseSectionDto.setCourse(CourseMapper.toCourseDto(courseSection.getCourse()));
        return courseSectionDto;
    }

    public static CourseSection toCourseSection(CourseSectionDto courseSectionDto) {
        if (courseSectionDto == null) return null;

        CourseSection courseSection = new CourseSection();
        courseSection.setUuid(courseSectionDto.getUuid());
        courseSection.setName(courseSectionDto.getName());
        courseSection.setDescription(courseSectionDto.getDescription());
        courseSection.setCourse(CourseMapper.toCourse(courseSectionDto.getCourse()));

        return courseSection;
    }
}
