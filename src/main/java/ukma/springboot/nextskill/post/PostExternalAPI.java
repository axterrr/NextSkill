package ukma.springboot.nextskill.post;

import org.springframework.modulith.NamedInterface;
import ukma.springboot.nextskill.models.responses.PostResponse;
import ukma.springboot.nextskill.models.views.PostView;

import java.util.UUID;

@NamedInterface
public interface PostExternalAPI {
    PostResponse get(UUID id);
    PostResponse create(PostView view);
    PostResponse update(PostView view);
    void delete(UUID id);
}
