package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.PostEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.views.PostView;

public class PostMapper {

    public static PostEntity toPostEntity(PostView postView) {
        return PostEntity.builder()
                .name(postView.getName())
                .content(postView.getContent())
                .section(SectionEntity.builder().uuid(postView.getSectionId()).build())
                .build();
    }

    public static PostResponse toPostResponse(PostEntity postEntity) {
        return PostResponse.builder()
                .uuid(postEntity.getUuid())
                .name(postEntity.getName())
                .createdAt(postEntity.getCreatedAt())
                .isHidden(postEntity.isHidden())
                .content(postEntity.getContent())
                .section(SectionMapper.toSectionResponse(postEntity.getSection()))
                .build();
    }
}
