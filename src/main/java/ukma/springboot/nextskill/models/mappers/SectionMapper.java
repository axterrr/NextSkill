package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;

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
                .posts(sectionEntity.getPosts().stream().map(PostMapper::toPostResponse).toList())
                .build();
    }
}
