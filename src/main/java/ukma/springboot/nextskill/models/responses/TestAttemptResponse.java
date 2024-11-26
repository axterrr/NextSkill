package ukma.springboot.nextskill.models.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TestAttemptResponse {
    private UUID uuid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean submitted;
    private UserResponse completedBy;
    private List<QuestionAnswerResponse> answers;
}
