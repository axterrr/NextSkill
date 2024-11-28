package ukma.springboot.nextskill.models.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAttemptView {
    private UUID uuid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean submitted;
    private UUID completedById;
    private UUID testId;
}
