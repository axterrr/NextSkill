package ukma.springboot.nextskill.models.views;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TestAttemptView {
    private UUID uuid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean submitted;
    private UUID completedById;
    private UUID testId;
}
