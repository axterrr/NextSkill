package ukma.springboot.nextskill.assessment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class OptionDeletedEvent extends ApplicationEvent {
    private final UUID testId;
    private final UUID optionId;

    public OptionDeletedEvent(Object source, UUID testId, UUID optionId) {
        super(source);
        this.testId = testId;
        this.optionId = optionId;
    }
}
