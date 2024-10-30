package ukma.springboot.nextskill.services.interfaces;

import ukma.springboot.nextskill.model.pojo.Post;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post createPost(Post post);
    Post updatePost(UUID id, Post post);
    List<Post> getAllPosts();
    Post getPostById(UUID postId);
    void deletePost(UUID postId);
}
