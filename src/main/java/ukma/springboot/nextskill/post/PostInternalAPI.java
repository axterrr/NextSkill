package ukma.springboot.nextskill.post;

import ukma.springboot.nextskill.models.responses.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostInternalAPI {
    List<PostResponse> getAll();
    PostResponse get(UUID id);
}
