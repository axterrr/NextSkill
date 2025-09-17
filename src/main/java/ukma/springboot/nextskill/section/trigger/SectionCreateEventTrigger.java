package ukma.springboot.nextskill.section.trigger;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.attempt.AttemptExternalAPI;
import ukma.springboot.nextskill.option.OptionDeletedEvent;
import ukma.springboot.nextskill.question.QuestionDeletedEvent;
import ukma.springboot.nextskill.section.SectionCreateEvent;
import ukma.springboot.nextskill.section.SectionExternalAPI;
import ukma.springboot.nextskill.section.SectionInternalAPI;

@Component
@AllArgsConstructor
public class SectionCreateEventTrigger {

    private final SectionExternalAPI sectionExternalAPI;

    @EventListener
    public void handleSectionCreateEvent(SectionCreateEvent event) {
        this.sectionExternalAPI.create(event.getSectionView());
    }
}
