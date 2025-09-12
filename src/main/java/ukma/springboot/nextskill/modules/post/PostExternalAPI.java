package ukma.springboot.nextskill.modules.post;

import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.views.PostView;

import java.util.UUID;

public interface PostExternalAPI {
    PostResponse get(UUID id);
    PostResponse create(PostView view);
    PostResponse update(PostView view);
    void delete(UUID id);
}
