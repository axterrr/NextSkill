package ukma.springboot.nextskill.modules.course;

import ukma.springboot.nextskill.models.responses.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface CourseInternalAPI {
    List<CourseResponse> getAll();
}
