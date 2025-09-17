package ukma.springboot.nextskill.section;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ukma.springboot.nextskill.models.views.SectionView;

@Getter
public class SectionCreateEvent extends ApplicationEvent {
    private final SectionView sectionView;

    public SectionCreateEvent(Object source, SectionView sectionView) {
        super(source);
        this.sectionView = sectionView;
    }
}
