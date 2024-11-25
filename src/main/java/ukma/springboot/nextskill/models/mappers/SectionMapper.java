package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.CourseEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.views.SectionView;

public class SectionMapper {

    public static SectionEntity toSectionEntity(SectionView sectionView) {
        return SectionEntity.builder()
                .name(sectionView.getName())
                .description(sectionView.getDescription())
                .course(CourseEntity.builder().uuid(sectionView.getCourseId()).build())
                .build();
    }
}
