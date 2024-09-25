package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.interfaces.IPostService;
import ukma.springboot.nextskill.model.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.entities.PostEntity;
import ukma.springboot.nextskill.repositories.PostRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }

    @Override
    public PostEntity updatePost(UUID id, PostEntity postDetails) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setAttachedFiles(postDetails.getAttachedFiles());

        return postRepository.save(post);
    }

    @Override
    public Post createPost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(UUID id, Post post) {
        return null;
    }

    @Override
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostEntity getPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
    }

    @Override
    public void deletePost(UUID postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        postRepository.delete(post);
    }
}
