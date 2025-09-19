package ukma.springboot.nextskill.post;

import ukma.springboot.nextskill.common.models.responses.PostResponse;
import ukma.springboot.nextskill.common.models.views.PostView;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponse> getAll();
    PostResponse get(UUID id);
    PostResponse create(PostView view);
    PostResponse update(PostView view);
    void delete(UUID id);
}
