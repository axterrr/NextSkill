package ukma.springboot.nextskill.course.validation;

import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.course.data.CourseView;
import ukma.springboot.nextskill.validation.GenericValidator;

@Component
public class CourseValidator extends GenericValidator<CourseView> {
}
