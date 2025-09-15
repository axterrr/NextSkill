package ukma.springboot.nextskill.section;

import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;

import java.util.UUID;

public interface SectionExternalAPI {
    SectionResponse get(UUID id);
    SectionResponse create(SectionView view);
    SectionResponse update(SectionView view);
    void delete(UUID id);
}
