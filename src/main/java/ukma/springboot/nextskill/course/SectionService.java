package ukma.springboot.nextskill.course;

import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;

import java.util.List;
import java.util.UUID;

public interface SectionService {
    List<SectionResponse> getAll();
    SectionResponse get(UUID id);
    SectionResponse create(SectionView view);
    SectionResponse update(SectionView view);
    void delete(UUID id);
}
