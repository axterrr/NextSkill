package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;

import static ukma.springboot.nextskill.models.mappers.MapperUtility.mapIfInitialized;
import static ukma.springboot.nextskill.models.mappers.MapperUtility.orElse;

public class SectionMapper {

    public static SectionEntity toSectionEntity(SectionView sectionView, SectionEntity sectionEntity) {
        return SectionEntity.builder()
                .uuid(sectionEntity.getUuid())
                .name(orElse(sectionView.getName(), sectionEntity.getName()))
                .description(orElse(sectionView.getDescription(), sectionEntity.getDescription()))
                .course(sectionEntity.getCourse())
                .posts(sectionEntity.getPosts())
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
        return SectionResponse.builder()
                .uuid(sectionEntity.getUuid())
                .name(sectionEntity.getName())
                .description(sectionEntity.getDescription())
                .course(CourseMapper.toCourseResponse(sectionEntity.getCourse()))
                .posts(mapIfInitialized(sectionEntity.getPosts(), PostMapper::toPostResponse))
                .build();
    }
}
