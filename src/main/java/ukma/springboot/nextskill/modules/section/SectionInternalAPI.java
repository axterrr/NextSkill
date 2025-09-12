package ukma.springboot.nextskill.modules.section;

import ukma.springboot.nextskill.models.responses.SectionResponse;

import java.util.List;
import java.util.UUID;

public interface SectionInternalAPI {
    List<SectionResponse> getAll();
}
