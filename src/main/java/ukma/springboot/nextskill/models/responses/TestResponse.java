package ukma.springboot.nextskill.models.responses;

import java.util.List;
import java.util.UUID;

public class TestResponse {
    private UUID uuid;
    private String name;
    private String description;
    private List<QuestionResponse> questions;
    private List<TestAttemptResponse> attempts;
}
