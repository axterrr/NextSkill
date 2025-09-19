package ukma.springboot.nextskill.common.models.mappers;

import ukma.springboot.nextskill.common.models.entities.CourseEntity;
import ukma.springboot.nextskill.course.mapper.CourseMapper;
import ukma.springboot.nextskill.common.models.entities.SectionEntity;
import ukma.springboot.nextskill.common.models.responses.SectionResponse;
import ukma.springboot.nextskill.common.models.views.SectionView;

import static ukma.springboot.nextskill.common.models.mappers.MapperUtility.mapIfInitialized;
import static ukma.springboot.nextskill.common.models.mappers.MapperUtility.orElse;

public class SectionMapper {

    private SectionMapper() {}

    public static SectionEntity toSectionEntity(SectionView sectionView, SectionEntity sectionEntity) {
        return SectionEntity.builder()
                .uuid(sectionEntity.getUuid())
                .name(orElse(sectionView.getName(), sectionEntity.getName()))
                .description(orElse(sectionView.getDescription(), sectionEntity.getDescription()))
                .course(sectionEntity.getCourse())
                .posts(sectionEntity.getPosts())
                .tests(sectionEntity.getTests())
                .build();
    }

    public static SectionEntity toSectionEntity(SectionView sectionView) {
        return SectionEntity.builder()
                .uuid(sectionView.getUuid())
                .name(sectionView.getName())
                .description(sectionView.getDescription())
                .course(CourseEntity.builder().uuid(sectionView.getCourseId()).build())
                .build();
    }

    public static SectionResponse toSectionResponse(SectionEntity sectionEntity) {
        if (sectionEntity == null) { return null; }
        return SectionResponse.builder()
                .uuid(sectionEntity.getUuid())
                .name(sectionEntity.getName())
                .description(sectionEntity.getDescription())
                .course(CourseMapper.toCourseResponse(sectionEntity.getCourse()))
                .posts(mapIfInitialized(sectionEntity.getPosts(), PostMapper::toPostResponseWithoutSection))
                .tests(mapIfInitialized(sectionEntity.getTests(), TestMapper::toTestResponseWithoutSection))
                .build();
    }

    public static SectionResponse toSectionResponseWithoutCourse(SectionEntity sectionEntity) {
        if (sectionEntity == null) { return null; }
        return SectionResponse.builder()
                .uuid(sectionEntity.getUuid())
                .name(sectionEntity.getName())
                .description(sectionEntity.getDescription())
                .posts(mapIfInitialized(sectionEntity.getPosts(), PostMapper::toPostResponseWithoutSection))
                .tests(mapIfInitialized(sectionEntity.getTests(), TestMapper::toTestResponseWithoutSection))
                .build();
    }
}
