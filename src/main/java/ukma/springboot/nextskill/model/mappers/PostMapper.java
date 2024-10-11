package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.entities.PostEntity;
import ukma.springboot.nextskill.model.Post;

public class PostMapper {

    private PostMapper() {}

    public static Post toPost(PostEntity postEntity) {
        Post post =  new Post(
                postEntity.getTitle(),
                postEntity.getUuid(),
                postEntity.getCreatedAt(),
                postEntity.isHidden(),
                postEntity.getOrder()
        );

        post.setAttachedFiles(postEntity.getAttachedFiles().stream()
                .map(FileUploadMapper::toFileUpload)
                .toList());

        post.setContent(post.getContent());
        return post;
    }

    public static PostEntity toPostEntity(Post post) {
        PostEntity postEntity =  new PostEntity(
                post.getUuid(),
                post.getTitle(),
                post.getOrder(),
                post.isHidden(),
                post.getCreatedAt()
                );

        postEntity.setAttachedFiles(post.getAttachedFiles().stream()
                .map(FileUploadMapper::toFileUploadEntity)
                .toList());

        postEntity.setContent(post.getContent());
        return postEntity;
    }
}

