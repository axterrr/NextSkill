package ukma.springboot.nextskill.services;

import ukma.springboot.nextskill.services.interfaces.IPostService;
import ukma.springboot.nextskill.model.pojo.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.model.entities.PostEntity;
import ukma.springboot.nextskill.model.mappers.FileUploadMapper;
import ukma.springboot.nextskill.model.mappers.PostMapper;
import ukma.springboot.nextskill.repositories.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        PostEntity postEntity = PostMapper.toPostEntity(post);
        return PostMapper.toPost(postRepository.save(postEntity));
    }

    @Override
    public Post updatePost(UUID id, Post postDetails) {
        Optional<PostEntity> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) throw new IllegalArgumentException("Post not found with id: " + id);

        PostEntity post = postOptional.get();

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setAttachedFiles(postDetails.getAttachedFiles()
                .stream().map(FileUploadMapper::toFileUploadEntity).toList());

        return PostMapper.toPost(postRepository.save(post));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll().stream().map(PostMapper::toPost).toList();
    }

    @Override
    public Post getPostById(UUID postId) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) throw new IllegalArgumentException("Post not found with id: " + postId);

        return PostMapper.toPost(postOptional.get());
    }

    @Override
    public void deletePost(UUID postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        postRepository.delete(post);
    }
}
