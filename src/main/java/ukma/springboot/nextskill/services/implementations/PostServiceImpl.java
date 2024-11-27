package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.PostEntity;
import ukma.springboot.nextskill.models.mappers.PostMapper;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.views.PostView;
import ukma.springboot.nextskill.repositories.PostRepository;
import ukma.springboot.nextskill.services.PostService;
import ukma.springboot.nextskill.validation.PostValidator;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private PostValidator postValidator;

    @Override
    public List<PostResponse> getAll() {
        return postRepository.findAll().stream().map(PostMapper::toPostResponse).toList();
    }

    @Override
    public PostResponse get(UUID id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));
        return PostMapper.toPostResponse(postEntity);
    }

    @Override
    public PostResponse create(PostView postView) {
        postValidator.validateForCreation(postView);
        PostEntity postEntity = postRepository.save(PostMapper.toPostEntity(postView));
        return PostMapper.toPostResponse(postEntity);
    }

    @Override
    public PostResponse update(PostView postView) {
        postValidator.validateForUpdate(postView);
        PostEntity existingPost = postRepository.findById(postView.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Post", postView.getUuid()));
        PostEntity postEntity = postRepository.save(PostMapper.toPostEntity(postView, existingPost));
        return PostMapper.toPostResponse(postEntity);
    }

    @Override
    public void delete(UUID id) {
        if (postRepository.findById(id).isEmpty()) {
            throw  new ResourceNotFoundException("Post", id);
        }
        postRepository.deleteById(id);
    }
}
