package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TestResponse {
    private UUID uuid;
    private String name;
    private String description;
    private List<QuestionResponse> questions;
    private List<TestAttemptResponse> attempts;
}
