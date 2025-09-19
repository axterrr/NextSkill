package ukma.springboot.nextskill.section.trigger;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.section.SectionCreateEvent;
import ukma.springboot.nextskill.course.service.SectionService;

@Component
@AllArgsConstructor
public class SectionCreateEventTrigger {

    private final SectionService sectionExternalAPI;

    @EventListener
    public void handleSectionCreateEvent(SectionCreateEvent event) {
        this.sectionExternalAPI.create(event.getSectionView());
    }
}
