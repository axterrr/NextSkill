package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;

import static ukma.springboot.nextskill.models.mappers.MapIfInitialized.mapIfInitialized;

public class SectionMapper {

    public static SectionEntity toSectionEntity(SectionView sectionView) {
        return SectionEntity.builder()
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
