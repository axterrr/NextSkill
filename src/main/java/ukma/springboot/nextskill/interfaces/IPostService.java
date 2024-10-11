package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.entities.PostEntity;
import ukma.springboot.nextskill.model.Post;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post createPost(Post post);
    Post updatePost(UUID id, Post post);
    List<Post> getAllPosts();
    Post getPostById(UUID postId);
    void deletePost(UUID postId);
}
