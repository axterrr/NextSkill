package ukma.springboot.nextskill.models.mappers;

import ukma.springboot.nextskill.models.entities.PostEntity;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.views.PostView;

import static ukma.springboot.nextskill.models.mappers.MapperUtility.orElse;

public class PostMapper {

    public static PostEntity toPostEntity(PostView postView, PostEntity postEntity) {
        return PostEntity.builder()
                .uuid(postEntity.getUuid())
                .name(orElse(postView.getName(), postEntity.getName()))
                .content(orElse(postView.getContent(), postEntity.getContent()))
                .isHidden(orElse(postView.getIsHidden(), postEntity.isHidden()))
                .section(postEntity.getSection())
                .build();
    }

    public static PostEntity toPostEntity(PostView postView) {
        return PostEntity.builder()
                .uuid(postView.getUuid())
                .name(postView.getName())
                .content(postView.getContent())
                .section(SectionEntity.builder().uuid(postView.getSectionId()).build())
                .build();
    }

    public static PostResponse toPostResponse(PostEntity postEntity) {
        if (postEntity == null) { return null; }
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
