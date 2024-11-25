package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.PostEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.views.PostView;

public class PostMapper {

    public static PostEntity toPostEntity(PostView postView) {
        return PostEntity.builder()
                .name(postView.getName())
                .content(postView.getContent())
                .section(SectionEntity.builder().uuid(postView.getSectionId()).build())
                .build();
    }
}
