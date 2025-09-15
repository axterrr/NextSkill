package ukma.springboot.nextskill.option;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class OptionChangedEvent extends ApplicationEvent {
    private final UUID testId;
    private final UUID optionId;

    public OptionChangedEvent(Object source, UUID testId, UUID optionId) {
        super(source);
        this.testId = testId;
        this.optionId = optionId;
    }
}
