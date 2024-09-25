package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.entities.PostEntity;
import ukma.springboot.nextskill.model.Post;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    PostEntity createPost(PostEntity post);

    // CRUD: Оновлення посту
    PostEntity updatePost(UUID id, PostEntity postDetails);

    Post createPost(Post post);
    Post updatePost(UUID id, Post post);
    List<PostEntity> getAllPosts();
    PostEntity getPostById(UUID postId);
    void deletePost(UUID postId);
}
